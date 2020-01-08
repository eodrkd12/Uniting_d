package com.example.commit.MainActivity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.ContextThemeWrapper
import android.view.View
import android.widget.ListView

import androidx.appcompat.app.AlertDialog

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.commit.Adapter.CategoryAdapter
import com.example.commit.Adapter.ChatRoomListAdapter
import com.example.commit.Class.UserInfo
import com.example.commit.R
import com.example.commit.Singleton.VolleyService
import kotlinx.android.synthetic.main.activity_open_chat_list.*
import org.json.JSONObject

class OpenChatListActivity : AppCompatActivity() {

    /*var chatRoomAdapter:ChatRoomListAdapter?=null
    var listChatRoom:ListView?=null*/


    init {
        INSTANCE=this
    }

    companion object{
        var CATEGORY:String="전체"
        var INSTANCE:OpenChatListActivity?=null
        var HANDLER:Handler?=null
    }

    lateinit var rvCategory: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_open_chat_list)

        btn_make.setOnClickListener(View.OnClickListener() {
            var intent = Intent(this, MakeRoomActivity::class.java)
            startActivity(intent)
        }
        )
        btn_search.setOnClickListener(View.OnClickListener {
            var intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)

        })

        rv_category.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rv_category.adapter = CategoryAdapter(this)
        rv_category.setHasFixedSize(true)

        var chatRoomAdapter= ChatRoomListAdapter()
        var listChatRoom=list_chat_room


        VolleyService.openChatRoomListReq(UserInfo.UNIV, CATEGORY, this, { success ->
            listChatRoom.adapter = chatRoomAdapter
            chatRoomAdapter.clear()

            var chatRoomArray = success
            if (chatRoomArray!!.length() == 0) {

            } else {
                for (i in 0..chatRoomArray.length() - 1) {
                    var json = chatRoomArray[i] as JSONObject
                    var roomId = json.getString("room_id")
                    var category = json.getString("cate_name")
                    var maker = json.getString("maker")
                    var roomTitle = json.getString("room_title")
                    var limitNum = json.getInt("limit_num")
                    var universityName = json.getString("univ_name")
                    var curNum = json.getInt("cur_num")

                    chatRoomAdapter.addItem(roomId, category, maker, roomTitle, limitNum, universityName, curNum)
                }
            }

            listChatRoom.setOnItemClickListener { parent, view, position, id ->
                var isFull = chatRoomAdapter.isFull(position)

                if (isFull) {
                    val builder =
                        AlertDialog.Builder(ContextThemeWrapper(this, R.style.Theme_AppCompat_Light_Dialog))
                    builder.setTitle("입장할 수 없습니다.")

                    builder.setPositiveButton("확인") { _, _ ->

                    }
                    builder.show()
                } else {
                    var roomId = chatRoomAdapter.getRoomId(position)
                    var category = chatRoomAdapter.getCategory(position)
                    var intent = Intent(this, ChatActivity::class.java)
                    intent.putExtra("room_id", roomId)
                    intent.putExtra("category", category)
                    startActivity(intent)
                }
            }

            chatRoomAdapter.notifyDataSetChanged()
        })

        HANDLER = object : Handler(){
            override fun handleMessage(msg: Message?) {
                Log.d("test","카테고리 메시지 도착 : ${msg!!.what}")
                when(msg!!.what){
                    0 -> {
                        //카테고리 방 갱신
                        VolleyService.openChatRoomListReq(UserInfo.UNIV,CATEGORY, INSTANCE!!,{ success ->
                            listChatRoom.adapter = chatRoomAdapter
                            chatRoomAdapter.clear()

                            var chatRoomArray = success
                            if (chatRoomArray!!.length() == 0) {

                            } else {
                                for (i in 0..chatRoomArray.length() - 1) {
                                    var json = chatRoomArray[i] as JSONObject
                                    var roomId = json.getString("room_id")
                                    var category = json.getString("cate_name")
                                    var maker = json.getString("maker")
                                    var roomTitle = json.getString("room_title")
                                    var limitNum = json.getInt("limit_num")
                                    var universityName = json.getString("univ_name")
                                    var curNum = json.getInt("cur_num")

                                    chatRoomAdapter.addItem(roomId, category, maker, roomTitle, limitNum, universityName, curNum)
                                }
                            }
                        })

                        chatRoomAdapter.notifyDataSetChanged()
                    }
                }
            }
        }
    }
}
