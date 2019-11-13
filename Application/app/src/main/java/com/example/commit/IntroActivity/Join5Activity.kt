package com.example.commit.IntroActivity

//프사 설정은 나중에 ..

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.commit.R
import com.example.commit.Singleton.VolleyService

class Join5Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join5)

        var getIntent=intent

        var id=getIntent.getStringExtra("id")
        var pw=getIntent.getStringExtra("pw")
        var name=getIntent.getStringExtra("name")
        var birthday=getIntent.getStringExtra("birthday")
        var gender=getIntent.getStringExtra("gender")
        var nickname=getIntent.getStringExtra("nickname")
        var webMail=getIntent.getStringExtra("univ_mail")
        var universityName=getIntent.getStringExtra("univ_name")
        var departmentName=getIntent.getStringExtra("dept_name")
        var enterYear=getIntent.getStringExtra("enter_year")

        Log.d("test",id)
        Log.d("test",pw)
        Log.d("test",name)
        Log.d("test",birthday)
        Log.d("test",gender)
        Log.d("test",nickname)
        Log.d("test",webMail)
        Log.d("test",universityName)
        Log.d("test",departmentName)
        Log.d("test",enterYear)

        VolleyService.joinReq(id,pw,name,birthday,gender,nickname,webMail,universityName,departmentName,enterYear,this,{success->
            if(success.equals("success")) {
                var intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
        })
    }
}
