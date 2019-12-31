package com.example.commit.Fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.example.commit.Adapter.ChatAdapter
import com.example.commit.Adapter.ChatRoomListAdapter
import com.example.commit.Class.UserInfo
import com.example.commit.MainActivity.ChatActivity
import com.example.commit.R
import com.example.commit.Singleton.VolleyService
import org.json.JSONArray
import org.json.JSONObject

class ChatFragment() : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_chat, container, false)

        var view=inflater.inflate(R.layout.fragment_chat,container,false)

        var chatRoomAdapter= ChatRoomListAdapter()
        var chatRoomArray: JSONArray?=null
        var listChatRoom:ListView=view.findViewById(R.id.list_chat_room)
        VolleyService.chatRoomListReq(UserInfo.NICKNAME,context!!,{ success ->
            listChatRoom.adapter=chatRoomAdapter
            chatRoomAdapter.clear()

            var chatRoomArray=success
            if(chatRoomArray!!.length()==0){

            }
            else{
                for(i in 0..chatRoomArray.length()-1){
                    var json=chatRoomArray[i] as JSONObject
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

            listChatRoom.setOnItemClickListener { parent, view, position, id ->
                var roomId=chatRoomAdapter.getRoomId(position)
                var intent= Intent(activity, ChatActivity::class.java)
                intent.putExtra("room_id",roomId)
                startActivity(intent)
            }

            chatRoomAdapter.notifyDataSetChanged()
        })

        return view
    }
}
