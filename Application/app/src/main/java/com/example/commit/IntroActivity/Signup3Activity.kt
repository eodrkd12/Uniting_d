package com.example.commit.IntroActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.commit.R
import kotlinx.android.synthetic.main.activity_signup3.*

class Signup3Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup3)

        var intent = intent

        var univMail = intent.getStringExtra("univMail")

        edit_signupwebmail.setText(univMail)
        edit_signupwebmail.setEnabled(false)



    }
}
