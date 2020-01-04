package com.example.commit.MainActivity

import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.view.View

import android.widget.LinearLayout
import android.widget.ListView

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.commit.Adapter.CategoryAdapter
import com.example.commit.Adapter.ChatRoomListAdapter
import com.example.commit.R
import com.example.commit.Singleton.VolleyService
import kotlinx.android.synthetic.main.activity_makeroom.*
import kotlinx.android.synthetic.main.activity_open_chat_list.*
import org.json.JSONArray
import org.json.JSONObject

class OpenChatListActivity : AppCompatActivity() {


    lateinit var rvCategory: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_open_chat_list)

        var chatRoomAdapter = ChatRoomListAdapter()
        var chatRoomArray: JSONArray? = null


       btn_make.setOnClickListener(View.OnClickListener(){
           var intent=Intent(this,MakeRoomActivity::class.java)
           startActivity(intent)
       }
       )
        btn_search.setOnClickListener(View.OnClickListener {
            var intent=Intent(this,SearchActivity::class.java)
            startActivity(intent)

       })

        rv_category.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rv_category.adapter = CategoryAdapter(this)
        rv_category.setHasFixedSize(true)

        VolleyService.chatRoomListReq("uniting", this, { success ->
            list_chat_room.adapter = chatRoomAdapter
            chatRoomAdapter.clear()

            chatRoomArray = success
            if (chatRoomArray!!.length() == 0) {

            } else {
                for (i in 0..chatRoomArray!!.length() - 1) {
                    var json = JSONObject()
                    json = chatRoomArray!![i] as JSONObject
                    var roomId = json.getString("room_id")
                    var cateName = json.getString("cate_name")
                    var maker = json.getString("maker")
                    var roomTitle = json.getString("room_title")
                    var limitNum = json.getInt("limit_num")
                    var universityName = json.getString("univ_name")
                    var curNum = json.getInt("cur_num")

                    chatRoomAdapter.addItem(roomId, cateName, maker, roomTitle, limitNum, universityName, curNum)
                }
            }

            list_chat_room.setOnItemClickListener { parent, view, position, id ->

            }

            chatRoomAdapter.notifyDataSetChanged()
        })


    }


    class CategorySave : Application(){
        companion object{
            var CATEGORY:String =""
        }
    }


}