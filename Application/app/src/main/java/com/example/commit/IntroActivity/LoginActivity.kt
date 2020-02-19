/*
Login Activity
1. 로고
2. 아이디 입력
3. 비밀번호 입력
4. 로그인 버튼
5. 아이디 / 비밀번호 찾기 -> 아이디 / 비번찾기 activity로 전환
6. 어케하노 -> 앱 소개(n개의 GuideActivity)로 전환
 */
package com.example.commit.IntroActivity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ContextThemeWrapper
import com.example.commit.Class.UserInfo
import com.example.commit.MainActivity.InformActivity
import com.example.commit.MainActivity.OpenChatListActivity
import com.example.commit.MainActivity.MainActivity
import com.example.commit.R
import com.example.commit.Singleton.VolleyService
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        //버튼에 리스너 등록(버튼을 클릭했을 때 동작하는 함수)
        // 인승추가(계정찾기 intent 설정)
        text_find.setOnClickListener {
            var intent:Intent=Intent(this,AcFindActivity::class.java)
            startActivity(intent)
        }
        //인승추가(도움말 버튼 클릭 시, 엑티비티 변환)
        text_guide.setOnClickListener {
            var intent:Intent=Intent(this,OpenChatListActivity::class.java)
            startActivity(intent)
        }

        text_join.setOnClickListener {
            var intent:Intent=Intent(this,Join1Activity::class.java)
            startActivity(intent)
        }

        btn_login.setOnClickListener  {
            //editText뷰로부터 값을 가져오는 과정
            //xml파일에서 선언한 뷰의 id를 입력해야 함(* xml파일마다 중복된 id가 있으니 안헷갈리게 주의)
            var id:String=edit_id.text.toString()
            var pw:String=edit_pw.text.toString()

            VolleyService.loginReq(id,pw,this, {success ->
                when(success.getInt("code")){
                    0 -> {
                        //통신 실패
                        Toast.makeText(this,"서버와의 통신에 실패했습니다.",Toast.LENGTH_SHORT).show()
                    }
                    1 -> {
                        //로그인 실패 : ID not exist
                        Toast.makeText(this,"계정을 확인해주세요.",Toast.LENGTH_SHORT).show()
                    }
                    2 -> {
                        //로그인 실패 : PW error
                        Toast.makeText(this,"ID / PW를 확인해주세요.",Toast.LENGTH_SHORT).show()
                    }
                    3 -> {
                        //로그인 성공
                        //프리퍼런스 저장
                        var user=success.getJSONObject("user")
                        UserInfo.ID=user.getString("user_id")
                        UserInfo.PW=user.getString("user_pw")
                        UserInfo.NAME=user.getString("user_name")
                        UserInfo.BIRTH=user.getString("user_birthday")
                        UserInfo.GENDER=user.getString("user_gender")
                        UserInfo.NICKNAME=user.getString("user_nickname")
                        UserInfo.EMAIL=user.getString("user_email")
                        UserInfo.UNIV=user.getString("univ_name")
                        UserInfo.ENTER=user.getString("enter_year")
                        UserInfo.DEPT=user.getString("dept_name")
                        UserInfo.IMG=user.getString("user_image")

                        var pref=this.getSharedPreferences("UserInfo", Context.MODE_PRIVATE)
                        var editor=pref.edit()
                        editor.putString("ID",UserInfo.ID)
                            .putString("PW",UserInfo.PW)
                            .putString("NAME",UserInfo.NAME)
                            .putString("BIRTH",UserInfo.BIRTH)
                            .putString("GENDER",UserInfo.GENDER)
                            .putString("NICKNAME",UserInfo.NICKNAME)
                            .putString("EMAIL",UserInfo.EMAIL)
                            .putString("UNIV",UserInfo.UNIV)
                            .putString("ENTER",UserInfo.ENTER)
                            .putString("DEPT",UserInfo.DEPT)
                            .putString("ING",UserInfo.IMG)
                            .apply()

                        //Intent클래스를 이용하여 화면 전환
                        //첫번째 파라미터는 this, 두번째 파라미터는 전환할 Activity)
                        var intent:Intent=Intent(this,MainActivity::class.java)
                        //전환하면서 데이터 보내는 구문
                        intent.putExtra("id",id)
                        intent.putExtra("pw",pw)
                        //전환하는 구문
                        startActivity(intent)
                    }
                }

            })
        }
    }

    override fun onBackPressed() {
        val builder = AlertDialog.Builder(ContextThemeWrapper(this@LoginActivity, R.style.Theme_AppCompat_Light_Dialog))
        //builder.setTitle(InformActivity.name)
        builder.setMessage("앱을 종료하시겠습니까?")

        builder.setNegativeButton("확인") { dialog, id->
            moveTaskToBack(true);
            /*finish();
            android.os.Process.killProcess(android.os.Process.myPid());*/
        }
        builder.setPositiveButton("취소") { dialog, id ->

        }
        builder.show()
    }
}
