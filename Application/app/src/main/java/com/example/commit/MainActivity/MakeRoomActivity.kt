package com.example.commit.MainActivity

import android.content.Context
import android.content.Intent
import android.os.Bundle
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
            var category = clicked_rbtn.text.toString()
            var introduce = text_introduce.text.toString()
            var maxNum = Integer.parseInt(text_max.text.toString())

            VolleyService.createOpenChatReq(
                UserInfo.NICKNAME,
                roomTitle,
                category,
                UserInfo.UNIV,
                introduce,
                maxNum,
                this,
                { success ->
                    var intent = Intent(this, OpenChatListActivity::class.java)
                    var roomId=success!!.get("room_id").toString()
                    intent.putExtra("room_id", roomId)
                    VolleyService.createFCMGroupReq(UserInfo.FCM_TOKEN,roomId!!,this,{ success ->
                        var roomPref=this.getSharedPreferences("Room", Context.MODE_PRIVATE)
                        var stringSet=roomPref.getStringSet("notification_key",null)
                        stringSet.add(success)

                        var editor=roomPref.edit()
                        editor.clear().commit()
                        editor.putStringSet("notification_key",stringSet).apply()
                        startActivity(intent)
                    })
                })
        }
    }
}
