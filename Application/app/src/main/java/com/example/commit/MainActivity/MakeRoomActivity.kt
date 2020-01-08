package com.example.commit.MainActivity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import com.example.commit.Class.UserInfo
import com.example.commit.R
import com.example.commit.Singleton.VolleyService
import kotlinx.android.synthetic.main.activity_makeroom.*

class MakeRoomActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_makeroom)


        rdgroup.check(R.id.r_btn_category1)

        btn_room.setOnClickListener {
            var clicked_rbtn = findViewById<RadioButton>(rdgroup.checkedRadioButtonId)

            var roomTitle = text_room_name.text.toString()
            var about = text_room_intro.text.toString()
            var category = clicked_rbtn.text.toString()
            var maxNum = Integer.parseInt(text_max.text.toString())

            Log.d("test", roomTitle)
            Log.d("test", about)
            Log.d("test", category)

            VolleyService.createOpenChatReq(
                UserInfo.NICKNAME,
                roomTitle,
                category,
                UserInfo.UNIV,
                maxNum,
                this,
                { success ->
                    var intent = Intent(this, OpenChatListActivity::class.java)
                    intent.putExtra("room_id",success!!.get("room_id").toString())
                    startActivity(intent)
                })
        }
    }
}
