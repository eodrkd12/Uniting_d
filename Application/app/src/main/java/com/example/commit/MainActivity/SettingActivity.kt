package com.example.commit.MainActivity

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import com.example.commit.IntroActivity.LoginActivity
import com.example.commit.R
import com.example.commit.Class.UserInfo
import com.example.commit.Singleton.VolleyService
import kotlinx.android.synthetic.main.activity_dating_on_off.*
import kotlinx.android.synthetic.main.activity_profile.*

class SettingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var intent=intent

        var tag=intent.getStringExtra("tag")

        when(tag){
            "프로필 보기" ->{
                setContentView(R.layout.activity_profile)

                var gender=""
                if(UserInfo.GENDER=="M"){
                    gender="남자"
                } else gender="여자"
                text_id.text=UserInfo.NICKNAME
                text_name.text=UserInfo.NAME+" / "+UserInfo.ID+" / "+gender
                text_university.text=UserInfo.UNIV

                VolleyService.getImageReq(UserInfo.NICKNAME,this, { success ->
                    val imageBytes = Base64.decode(success, 0)
                    val image = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)

                    image_profile.setImageBitmap(image)
                })
            }
            "알림 설정"->{
                setContentView(R.layout.activity_alam_setting)
            }
            "문의하기"->{
                setContentView(R.layout.activity_ask)
            }
            "앱 버전"->{
                setContentView(R.layout.activity_app_version)
            }
            "공지사항"->{
                setContentView(R.layout.activity_notice)
            }
            "커뮤니티 이용규칙"->{
                setContentView(R.layout.activity_rule)
            }
            "개인정보 처리방침"->{
                setContentView(R.layout.activity_rule2)
            }
            "정보 수신 동의 "->{
                setContentView(R.layout.activity_agreement)
            }
            "회원 탈퇴"->{
                setContentView(R.layout.activity_withdrawal)
            }
            "로그아웃"->{
                var pref=this.getSharedPreferences("UserInfo", Context.MODE_PRIVATE)
                var editor=pref.edit()

                editor.clear()
                editor.commit()

                var intent= Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
        }
    }
}
