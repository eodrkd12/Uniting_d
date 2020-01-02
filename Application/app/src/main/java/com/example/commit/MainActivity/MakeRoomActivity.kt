package com.example.commit.MainActivity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.commit.R
import com.example.commit.Singleton.VolleyService
import kotlinx.android.synthetic.main.activity_makeroom.*

class MakeRoomActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_makeroom)

      btn_room.setOnClickListener {
            var name = text_room_name.text.toString()
            var about= text_room_intro.text.toString()

            Log.d("test",name)
            Log.d("test",about)

          /* VolleyService.roomReq(name,about,this,{ success->
                if(success.equals("success")) {
                    var intent = Intent(this,OpenChatListActivity::class.java)
                    startActivity(intent)
                }
             }
        })*/
     }
    }
}
