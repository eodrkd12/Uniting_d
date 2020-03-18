package com.example.commit.MainActivity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ContextThemeWrapper
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.toolbox.Volley
import com.example.commit.Adapter.ChatAdapter
import com.example.commit.Class.UserInfo
import com.example.commit.Popup.ReportPopup
import com.example.commit.R
import com.example.commit.Singleton.VolleyService
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_chat.*
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import kotlin.collections.HashMap

class ChatActivity : AppCompatActivity() {

    var chatAdapter=ChatAdapter()
    var roomId:String?=null
    var category:String?=null
    var title:String?=null
    var notificationKey:String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        var intent= intent

        roomId=intent.getStringExtra("room_id")
        category=intent.getStringExtra("category")
        title=intent.getStringExtra("title")

        VolleyService.getNotificationKeyReq(roomId!!,this,{success ->
            Log.d("test","success : ${success}")
            notificationKey=success
            Log.d("test","notificationkey : ${notificationKey}")

            var roomPref=this.getSharedPreferences("Room", Context.MODE_PRIVATE)
            var stringSet=roomPref.getStringSet("notification_key",null)
            Log.d("test","stringSet : ${stringSet.size}")

            if(stringSet==null||!stringSet.contains(notificationKey)){
                VolleyService.updateFCMGroupReq("add",notificationKey!!,UserInfo.FCM_TOKEN,roomId!!,this)
                stringSet.add(notificationKey)
                var editor=roomPref.edit()
                editor.clear().commit()
                editor.putStringSet("notification_key",stringSet).apply()
            }

            VolleyService.getJoinTimeReq(roomId!!,UserInfo.NICKNAME,this,{success ->
                val ref = FirebaseDatabase.getInstance().reference.child("chat").child(roomId!!)
                val query=ref.orderByChild("fulltime").startAt(success,"fulltime")

                val childEventListener=object : ChildEventListener{
                    override fun onCancelled(p0: DatabaseError) {
                    }
                    override fun onChildMoved(p0: DataSnapshot, p1: String?) {
                    }
                    override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                        chatConversation(p0)
                    }
                    override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                        chatConversation(p0)
                    }
                    override fun onChildRemoved(p0: DataSnapshot) {
                    }
                }

                query.addChildEventListener(childEventListener)

                btn_send.setOnClickListener {
                    if(edit_chat.text.toString()!="") {

                        var map = HashMap<String, Any>()

                        val key: String? = ref.push().key

                        ref.updateChildren(map)

                        var root = ref.child(key!!)
                        var objectMap = HashMap<String, Any>()

                        val current = ZonedDateTime.now(ZoneId.of("Asia/Seoul"))
                        val noon = current.format(DateTimeFormatter.ofPattern("a"))
                        var formatter: DateTimeFormatter? = null
                        if (noon == "PM")
                            formatter = DateTimeFormatter.ofPattern("오후 hh:mm")
                        else
                            formatter = DateTimeFormatter.ofPattern("오전 hh:mm")
                        val formatted = current.format(formatter)
                        val fulltime=current.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))

                        objectMap.put("room_id", roomId!!)
                        objectMap.put("speaker", UserInfo.NICKNAME)
                        objectMap.put("content", edit_chat.text.toString())
                        objectMap.put("time", formatted)
                        objectMap.put("fulltime",fulltime)

                        root.updateChildren(objectMap)

                        VolleyService.sendFCMReq(notificationKey!!,title!!,"${UserInfo.NICKNAME} : ${edit_chat.text}" , this)

                        edit_chat!!.setText("")
                        list_chat.setSelection(chatAdapter.count-1)
                    }
                }
            })
        })

        text_title.text=title

        list_chat.adapter=chatAdapter

    }

    fun chatConversation(dataSnapshot: DataSnapshot){
        var i=dataSnapshot.children.iterator()

        while(i.hasNext()){

            var content=((i.next() as DataSnapshot).getValue()) as String
            var fulltime=((i.next() as DataSnapshot).getValue()) as String
            var roomId=((i.next() as DataSnapshot).getValue()) as String
            var speaker=((i.next() as DataSnapshot).getValue()) as String
            var time=((i.next() as DataSnapshot).getValue()) as String

            /*var stringImage:String?=null

            VolleyService.getImageReq(speaker,this,{success ->
                stringImage=success
            })
            chatAdapter.addItem(roomId,speaker, content, time, fulltime,stringImage!!)*/
            chatAdapter.addItem(roomId,speaker, content, time, fulltime)
        }
        chatAdapter.notifyDataSetChanged()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        var inflater=getMenuInflater()
        inflater.inflate(R.menu.menu_chat,menu)

        menu!!.add("참여중인 유저").setEnabled(false)

        menu.add(UserInfo.NICKNAME)

        VolleyService.getUserInRoom(roomId!!,UserInfo.NICKNAME,this,{success ->
            for(i in 0..success!!.length()-1){
                var jsonObject=success.getJSONObject(i)
                var nickname=jsonObject.getString("user_nickname")
                var item=menu.add(nickname)
                item.setOnMenuItemClickListener {
                    var intent=Intent(this, ReportPopup::class.java)
                    intent.putExtra("nickname",nickname)
                    startActivity(intent)
                    true
                }
            }

            var exit=menu.add("나가기")
            exit.setIcon(R.drawable.icon_exit_room)
            exit.setOnMenuItemClickListener {
                VolleyService.exitReq(UserInfo.NICKNAME,roomId!!,this,{success -> })

                if(category=="데이팅")
                    VolleyService.datingExitReq(UserInfo.NICKNAME,this)

                VolleyService.updateFCMGroupReq("remove",notificationKey!!,UserInfo.FCM_TOKEN,roomId!!,this)
                var roomPref=this.getSharedPreferences("Room",Context.MODE_PRIVATE)
                var editor=roomPref.edit()
                var stringSet=roomPref.getStringSet("notification_key",null)
                if(stringSet==null){
                    Log.d("test","방 나가기 오류 : Preference에 데이터가 없습니다.")
                }
                else{
                    stringSet.remove(notificationKey)
                    editor.clear().commit()
                    editor.putStringSet("notification_key",stringSet).apply()
                }

                var intent=Intent(this,MainActivity::class.java)
                startActivity(intent)

                true
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        /*var title=item!!.title.toString()
        when(title){
            "exit_room" -> {
                VolleyService.exitReq(UserInfo.NICKNAME,roomId!!,this,{success ->

                })

                if(category=="데이팅") {
                    VolleyService.datingExitReq(UserInfo.NICKNAME,this)
                }

                var intent=Intent(this,MainActivity::class.java)
                startActivity(intent)
            }
        }
        */
        return true
    }
}