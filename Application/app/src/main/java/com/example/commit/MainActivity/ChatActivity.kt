package com.example.commit.MainActivity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.commit.Adapter.ChatAdapter
import com.example.commit.Class.UserInfo
import com.example.commit.R
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_chat.*
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import kotlin.collections.HashMap

class ChatActivity : AppCompatActivity() {

    var chatAdapter=ChatAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        var intent= intent

        var roomId=intent.getStringExtra("room_id")

        val ref = FirebaseDatabase.getInstance().reference.child("chat")
        val query=ref.orderByChild("room_id").equalTo(roomId)
        list_chat.adapter=chatAdapter

        val childEventListener=object : ChildEventListener{
            override fun onCancelled(p0: DatabaseError) {
            }
            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
            }
            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                chatConversation(p0)
            }
            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                Log.d("test","tt")
                chatConversation(p0)
            }
            override fun onChildRemoved(p0: DataSnapshot) {
            }
        }

        query.addChildEventListener(childEventListener)

        btn_send.setOnClickListener {
            var map= HashMap<String,Any>()

            val key:String?=ref.push().key

            ref.updateChildren(map)

            var root=ref.child(key!!)
            var objectMap= HashMap<String,Any>()


            val current = ZonedDateTime.now(ZoneId.of("Asia/Seoul"))
            val noon=current.format(DateTimeFormatter.ofPattern("a"))
            var formatter:DateTimeFormatter?=null
            if(noon=="PM")
                formatter = DateTimeFormatter.ofPattern("오후 hh:mm")
            else
                formatter = DateTimeFormatter.ofPattern("오전 hh:mm")
            val formatted = current.format(formatter)

            objectMap.put("room_id",roomId!!)
            objectMap.put("speaker",UserInfo.NICKNAME)
            objectMap.put("content",edit_chat.text.toString())
            objectMap.put("time",formatted)

            root.updateChildren(objectMap)
            edit_chat!!.setText("")
        }
    }

    fun chatConversation(dataSnapshot: DataSnapshot){
        var i=dataSnapshot.children.iterator()

        while(i.hasNext()){

            var content=((i.next() as DataSnapshot).getValue()) as String
            var roomId=((i.next() as DataSnapshot).getValue()) as String
            var speaker=((i.next() as DataSnapshot).getValue()) as String
            var time=((i.next() as DataSnapshot).getValue()) as String

            chatAdapter.addItem(roomId,speaker, content, time)
        }

        chatAdapter.notifyDataSetChanged()
    }
}