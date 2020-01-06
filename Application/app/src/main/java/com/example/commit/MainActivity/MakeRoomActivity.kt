package com.example.commit.MainActivity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.commit.Class.UserInfo
import com.example.commit.R
import com.example.commit.Singleton.VolleyService
import kotlinx.android.synthetic.main.activity_makeroom.*

class MakeRoomActivity : AppCompatActivity() {

    /* var btn_out="";
     var rg="";*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_makeroom)



     /*   rg = (RadioGroup) findViewById(R.id.rdgroup);

        btn_out = (Button) findViewById(R.id.btn_out);
        btn_out.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                //public int getCheckedRadioButtonId () : 선택된 라디오버튼의 ID값을 반환
                RadioButton rd = (RadioButton) findViewById(rg.getCheckedRadioButtonId());
                var str_Qtype = rd.getText().toString();


                Toast.makeText(getApplicationContext(), str_Qtype+" 선택됨",
                    Toast.LENGTH_SHORT).show();
            }
        });*/


      btn_room.setOnClickListener {
            var roomTitle = text_room_name.text.toString()
            var about= text_room_intro.text.toString()
            var category = " "                              //카테고리 체크시 text 받아오기 체크 하나씩만
           var  maxrange = text_max.text.toString()


            Log.d("test",roomTitle)
            Log.d("test",about)


          VolleyService.rcreateOpenChatReq(UserInfo.NICKNAME,roomTitle,category,UserInfo.UNIV,maxrange,this,{ success ->
                if(success.equals("success")) {
                    var intent = Intent(this,OpenChatListActivity::class.java)
                    startActivity(intent)
                }

        })
    }
    }
}
