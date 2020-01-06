package com.example.commit.MainActivity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.commit.Class.UserInfo
import com.example.commit.R
import com.example.commit.Singleton.VolleyService
import kotlinx.android.synthetic.main.activity_makeroom.*
import kotlinx.android.synthetic.main.activity_my_post.*

class MakeRoomActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_makeroom)


        rdgroup.check(R.id.r_btn_category1)

        btn_room.setOnClickListener {
            var clicked_rbtn = findViewById<RadioButton>(rdgroup.checkedRadioButtonId)

            var roomTitle = text_room_name.text.toString()
            var about = text_room_intro.text.toString()
            var category = clicked_rbtn.text.toString()                             //카테고리 체크시 text 받아오기 체크 하나씩만
            var maxrange = text_max.text.toString().toInt()




            Log.d("test", roomTitle)
            Log.d("test", about)
            Log.d("test", category)


          /*  VolleyService.createOpenChatReq(UserInfo.NICKNAME,roomTitle,category,UserInfo.UNIV,maxrange,this,{ success ->
                  if(success.equals("success")) {
                      var intent = Intent(this,OpenChatListActivity::class.java)
                      startActivity(intent)
                  }

          })*/
        }
    }
}
