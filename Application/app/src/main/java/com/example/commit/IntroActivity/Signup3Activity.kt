package com.example.commit.IntroActivity

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.Spanned
import android.text.TextWatcher
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.commit.R
import com.example.commit.Singleton.VolleyService
import kotlinx.android.synthetic.main.activity_signup3.*
import org.jetbrains.anko.spinner
import java.util.regex.Pattern

class Signup3Activity : AppCompatActivity() {

    var idCheck : Int = 0
    var pw1Check : Int = 0
    var pw2Check : Int = 0
    var nickCheck : Int = 0
    var enteryear : String = ""
    var gender: String = ""
    var yearList = ArrayList<String>()

    class IdFilter : InputFilter {
        override fun filter(source: CharSequence, start: Int, end: Int, dest: Spanned, dstart: Int, dend: Int) : CharSequence?{
            var ps : Pattern = Pattern.compile("^[a-zA-Z0-9]+$")
            if(!ps.matcher(source).matches()) {
                return ""
            }
            return null
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup3)

        var intent = intent
        var univMail = intent.getStringExtra("univMail")

        edit_signupwebmail.setText(univMail)
        edit_signupwebmail.setEnabled(false)


        //아이디 특수문자 체크
        edit_signupid.setFilters(arrayOf<InputFilter>(IdFilter()))

        //아이디 중복체크
        btn_signupidcheck.setOnClickListener{
            VolleyService.idCheckReq(edit_signupid.text.toString(), this@Signup3Activity,{ success->
                if(success == 0)
                {
                    text_signupidcheck.text = "중복된 아이디 입니다."
                    text_signupidcheck.setTextColor(Color.parseColor("#FF0000"))
                    idCheck = 0
                }
                else if(success == 1)
                {
                    text_signupidcheck.text = "사용가능한 아이디입니다."
                    text_signupidcheck.setTextColor(Color.parseColor("#008000"))
                    idCheck = 1
                }
            })
        }

        edit_signupid.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                idCheck = 0
                text_signupidcheck.text = ""
            }
        })

        edit_signuppw.setOnFocusChangeListener { view, b ->
            if(b == false)
            {
                if(edit_signuppw.text.length < 8)
                {
                    pw1Check = 0
                    text_signuppwcriteria.text = "비밀번호는 8자리 이상이어야 합니다."
                    text_signuppwcriteria.setTextColor(Color.parseColor("#FF0000"))
                }
                else
                {
                    pw1Check = 1
                    text_signuppwcriteria.text = "사용가능한 비밀번호 입니다."
                    text_signuppwcriteria.setTextColor(Color.parseColor("#008000"))
                }
            }
        }

        /*edit_signuppw.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if(edit_signuppw.text.length < 8)
                {
                    pw1Check = 0
                    text_signuppwcriteria.text = "비밀번호는 8자리 이상이어야 합니다."
                    text_signuppwcriteria.setTextColor(Color.parseColor("#FF0000"))
                }
                else
                {
                    pw1Check = 1
                    text_signuppwcriteria.text = "사용가능한 비밀번호 입니다."
                    text_signuppwcriteria.setTextColor(Color.parseColor("#008000"))
                }
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                edit_signuppwcheck.text.clear()
                text_signuppwcheck.text = ""
                pw2Check = 0
            }
        })*/

        edit_signuppwcheck.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if(edit_signuppw.text.toString().equals(edit_signuppwcheck.text.toString()) && (pw1Check == 1))
                {
                    text_signuppwcheck.text = "비밀번호가 일치합니다."
                    text_signuppwcheck.setTextColor(Color.parseColor("#008000"))
                    pw2Check = 1
                }
                else{
                    text_signuppwcheck.text = "비밀번호가 일치하지 않습니다."
                    text_signuppwcheck.setTextColor(Color.parseColor("#FF0000"))
                    pw2Check = 0
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })

        btn_signupnicknamecheck.setOnClickListener{
            VolleyService.nicknameCheckReq(edit_signupnickname.text.toString(), this, {success->
                if(success == 0)
                {
                    text_signupnicknamecheck.text = "중복된 닉네임 입니다."
                    text_signupnicknamecheck.setTextColor(Color.parseColor("#FF0000"))
                    nickCheck = 0
                }
                else if(success == 1)
                {
                    text_signupnicknamecheck.text = "사용가능한 닉네임 입니다."
                    text_signupnicknamecheck.setTextColor(Color.parseColor("#008000"))
                    nickCheck = 1
                }
            })
        }

        edit_signupnickname.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                text_signupnicknamecheck.text = ""
                nickCheck = 0
            }
        })

        radiogroup_gender.setOnCheckedChangeListener {radioGroup, i ->
            when(i) {
                R.id.radio_male -> gender = "M"
                R.id.radio_female -> gender = "F"
            }
        }

        for(i in 2010..2020) {
            yearList.add(i.toString())
        }

        var adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, yearList)
        spinner_year.adapter = adapter


        btn_signup.setOnClickListener{
            enteryear = spinner_year.selectedItem.toString()

            if(idCheck == 0)
            {
                Toast.makeText(this@Signup3Activity, "아이디를 확인해주세요.", Toast.LENGTH_SHORT).show()
            }
            else if(pw2Check == 0 || pw1Check == 0)
            {
                Toast.makeText(this, "비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show()
            }
            else if(nickCheck == 0)
            {
                Toast.makeText(this, "닉네임을 확인해주세요.", Toast.LENGTH_SHORT).show()
            }
            else if(gender == "")
            {
                Toast.makeText(this, "성별을 선택해주세요.", Toast.LENGTH_SHORT).show()
            }
            else if(enteryear == "")
            {
                Toast.makeText(this, "입학년도를 선택해주세요.", Toast.LENGTH_SHORT).show()
            }
        }



    }
}
