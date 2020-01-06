package com.example.commit.MainActivity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.commit.Class.UserInfo
import com.example.commit.R
import com.example.commit.Singleton.VolleyService
import kotlinx.android.synthetic.main.activity_makeroom.*

class MakeRoomActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_makeroom)



      btn_room.setOnClickListener {
            var roomTitle = text_room_name.text.toString()
            var about= text_room_intro.text.toString()
            var category = " "                              //카테고리 체크시 text 받아오기
          var  maxrange = text_max.text.toString()

            Log.d("test",roomTitle)
            Log.d("test",about)


          VolleyService.rcreateOpenChatReq(UserInfo.NICKNAME,roomTitle,category,UserInfo.UNIV,maxrange,this,{ success ->
                if(success.equals("success")) {
                    var intent = Intent(this,OpenChatListActivity::class.java)
                    startActivity(intent)
                }

        })
    }
    }
}
