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

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
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
            var intent:Intent=Intent(this,MainActivity::class.java)
            startActivity(intent)
        }

        btn_login.setOnClickListener  {
            //editText뷰로부터 값을 가져오는 과정
            //xml파일에서 선언한 뷰의 id를 입력해야 함(* xml파일마다 중복된 id가 있으니 안헷갈리게 주의)
            var id:String=edit_id.text.toString()
            var pw:String=edit_pw.text.toString()

            VolleyService.loginReq(id,pw,this, {success ->
                when(success){
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
}
