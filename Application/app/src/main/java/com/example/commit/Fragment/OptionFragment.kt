package com.example.commit.Fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.example.commit.Adapter.MypageAdapter
import com.example.commit.MainActivity.DatingActivity
import com.example.commit.MainActivity.SettingActivity
import com.example.commit.R

class mypage_list (var memu :String)
var mypage_item = arrayListOf<mypage_list>()

class OptionFragment():Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view=inflater.inflate(R.layout.fragment_mypage,container,false)

        var listView=view.findViewById<ListView>(R.id.list_mypage)
        var mypageAdapter=MypageAdapter()
        listView.adapter=mypageAdapter

        mypageAdapter.addItem("프로필 보기")
        mypageAdapter.addItem("데이팅 on/off")
        mypageAdapter.addItem("알림 설정")
        mypageAdapter.addItem("문의하기")
        mypageAdapter.addItem("앱 버전")
        mypageAdapter.addItem("공지사항")
        mypageAdapter.addItem("커뮤니티 이용규칙")
        mypageAdapter.addItem("개인정보 처리방침")
        mypageAdapter.addItem("정보 수신 동의")
        mypageAdapter.addItem("로그아웃")
        mypageAdapter.addItem("회원탈퇴")


        listView.setOnItemClickListener { parent, view, position, id ->
            var tag=mypageAdapter.getName(position)



            var intent=Intent(activity!!,SettingActivity::class.java)
            intent.putExtra("tag",tag)
            startActivity(intent)
        }


        //mypageAdapter.getItemId()
       /* var click=View.OnClickListener{
            var intent= Intent(activity,  DatingActivity::class.java)
        }*/

        // Inflate the layout for this fragment
        return view
    }


}
