package com.example.commit.MainActivity

import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.commit.Adapter.ChatRoomListAdapter
import com.example.commit.R
import com.example.commit.Singleton.VolleyService
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.android.synthetic.main.activity_chat_room_list.*
import org.json.JSONArray
import org.json.JSONObject

class ChatRoomListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_room_list)

        var chatRoomAdapter= ChatRoomListAdapter()
        var chatRoomArray: JSONArray?=null

        VolleyService.chatRoomListReq("uniting",this,{success ->
            list_chat_room.adapter=chatRoomAdapter
            chatRoomAdapter.clear()

            chatRoomArray=success
            if(chatRoomArray!!.length()==0){

            }
            else{
                for(i in 0..chatRoomArray!!.length()-1){
                    var json= JSONObject()
                    json=chatRoomArray!![i] as JSONObject
                    var roomId=json.getString("room_id")
                    var cateName=json.getString("cate_name")
                    var maker=json.getString("maker")
                    var roomTitle=json.getString("room_title")
                    var limitNum=json.getInt("limit_num")
                    var universityName=json.getString("univ_name")
                    var curNum=json.getInt("cur_num")

                    chatRoomAdapter.addItem(roomId,cateName,maker,roomTitle,limitNum,universityName,curNum)
                }
            }

            list_chat_room.setOnItemClickListener { parent, view, position, id ->

            }

            chatRoomAdapter.notifyDataSetChanged()
        })
    }
}
