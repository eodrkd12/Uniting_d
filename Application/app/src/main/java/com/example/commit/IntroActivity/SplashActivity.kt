package com.example.commit.IntroActivity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.StrictMode
import com.example.commit.R

class SplashActivity: AppCompatActivity() {
    val Duration:Long = 2000
    //onCreate : 액티비티가 생성될 때 실행되는 메소드
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView : 액티비티와 연결되는 레이아웃파일을 설정
        setContentView(R.layout.activity_splash)

        //스마트폰 인터넷 사용 권한 허가
        StrictMode.setThreadPolicy(
            StrictMode.ThreadPolicy.Builder()
                .permitDiskReads()
                .permitDiskWrites()
                .permitNetwork()
                .build())

        Handler().postDelayed({
            var intent:Intent = Intent(this,IntroActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)  //액티비티 전환시 애니메이션을 무시
            startActivity(intent)
            finish()
        },Duration)
    }
}