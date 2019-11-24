package com.example.commit.MainActivity

import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Debug
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.ContextThemeWrapper
import com.example.commit.Adapter.DatingAdapter
import com.example.commit.Adapter.MarketAdapter
import com.example.commit.Adapter.StudyAdapter
import com.example.commit.ListItem.DatingItem
import com.example.commit.R
import com.example.commit.Singleton.VolleyService
import kotlinx.android.synthetic.main.activity_dating.*
import org.json.JSONArray
import org.json.JSONObject
import java.util.*

class DatingActivity : AppCompatActivity() {

    var datingAdapter=DatingAdapter()
    var datingArray:JSONArray?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dating)

        var intent=intent
        var tag=intent.getStringExtra("tag")

        when(tag){
            //데이팅 리스트 표시
            "DATING" -> {
                VolleyService.datingUserReq("uniting","M","kmu",this,{success ->
                    list_content.adapter=datingAdapter
                    datingAdapter.clear()

                    datingArray=success

                    if(datingArray!!.length()==0){

                    }
                    else{
                        for(i in 0..datingArray!!.length()-1){
                            var json=JSONObject()
                            json=datingArray!![i] as JSONObject
                            var nickname=json.getString("user_nickname")
                            var department=json.getString("dept_name")
                            //현재 연도 구하기
                            var calendar=GregorianCalendar(Locale.KOREA)
                            var year=calendar.get(Calendar.YEAR)
                            //이용자 생일 구하기
                            var birthday=json.getString("user_birthday")
                            birthday=birthday.substring(0,4)

                            //이용자 나이 계산
                            var age=year-Integer.parseInt(birthday)+1
                            datingAdapter.addItem(nickname,department,age)
                        }
                    }

                    datingAdapter.notifyDataSetChanged()

                    list_content.setOnItemClickListener { parent, view, position, id ->
                        var userNickname=datingAdapter.getNickname(position)
                        val builder = AlertDialog.Builder(ContextThemeWrapper(this@DatingActivity, R.style.Theme_AppCompat_Light_Dialog))
                        builder.setTitle("${userNickname}님과의 대화")
                        builder.setMessage("시작하시겠습니까?")

                        builder.setPositiveButton("확인") { _, _ ->
                            VolleyService.createDatingReq("uniting",userNickname!!,"kmu",this, { success ->
                                var roomId=success!!.getString("room_id")
                                var intent= Intent(this,ChatActivity::class.java)
                                intent.putExtra("room_id",roomId)
                                startActivity(intent)
                            })
                        }
                        builder.setNegativeButton("취소") { _, _ ->

                        }
                        builder.show()
                    }
                })
            }
        }
    }
}
/*
//마켓 OR STUDY 게시글 리스트 표시
            else -> {
                VolleyService.postReq(tag,this,{success ->
                    if(tag=="MARKET") {
                        list_content.adapter = marketAdapter
                        marketAdapter.clear()
                    }
                    else{
                        list_content.adapter = studyAdapter
                        studyAdapter.clear()
                    }
                    contentArray=success

                    if(contentArray!!.length()==0){

                    }
                    else{
                        for(i in 0..contentArray!!.length()-1){
                            var json=JSONObject()
                            json=contentArray!![i] as JSONObject
                            var title=json.getString("title")
                            var writer=json.getString("writer")

                            if(tag=="MARKET") marketAdapter.addItem(title,writer)
                            else studyAdapter.addItem(title,writer)
                        }
                    }

                    if(tag=="MARKET") marketAdapter.notifyDataSetChanged()
                    else studyAdapter.notifyDataSetChanged()
                })
            }
 */