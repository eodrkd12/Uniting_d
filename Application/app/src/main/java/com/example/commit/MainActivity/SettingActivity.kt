package com.example.commit.MainActivity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.commit.IntroActivity.LoginActivity
import com.example.commit.R
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
            }
            "데이팅"->{
                setContentView(R.layout.activity_dating_on_off)

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
