package com.example.commit.MainActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.commit.Class.GMailSender
import com.example.commit.Popup.ReportPopup
import com.example.commit.R
import com.example.commit.Singleton.VolleyService
import kotlinx.android.synthetic.main.activity_join2.*
import kotlinx.android.synthetic.main.activity_report.*

class ReportActivity : AppCompatActivity() {

    //var webMail:String?=null;
    var code:String=""
    var nickname= ""
    var reason=text_reason.text.toString()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report)

        nickname=intent.getStringExtra("nickname")

        btn_webmail.setOnClickListener {
          // webMail = edit_webmail.text.toString() + text_address.text.toString()

           if (edit_webmail.text.toString() != "") {

                var mailSender: GMailSender = GMailSender("eodrkd12@gmail.com", "ioioko123!",code)
                mailSender.sendMail(
                    "채팅방신고"
                    , "신고한 아이디:" + nickname+
                            "신고사유:"+reason

                   , "eodrkd12@gmail.com"
                )


            } else {
                Toast.makeText(this, "올바른 이메일을 입력해주세요.", Toast.LENGTH_SHORT).show()
            }

            //Log.d("test", "메일전송 : ${webMail}")
        }
    }
    }