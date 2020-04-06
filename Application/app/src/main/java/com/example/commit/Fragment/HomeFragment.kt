package com.example.commit.Fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.commit.Class.UserInfo
import com.example.commit.MainActivity.ChatActivity

import com.example.commit.MainActivity.DatingActivity
import com.example.commit.MainActivity.OpenChatListActivity
import com.example.commit.R
import com.example.commit.Singleton.VolleyService
import com.google.firebase.messaging.FirebaseMessaging
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.fragment_home.*
import org.json.JSONObject
import java.util.*

class HomeFragment() : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Log.d("uniting","MainActivity->HomeFragment : ${UserInfo.NICKNAME} : ${UserInfo.FCM_TOKEN}")

        var view=inflater.inflate(R.layout.fragment_home,container,false)


        var btnDating=view.findViewById<Button>(R.id.btn_dating)
        var btnOpen=view.findViewById<Button>(R.id.btn_open)

        var textPartnerState=view.findViewById<TextView>(R.id.text_partner_state)
        var textPartnerNull=view.findViewById<TextView>(R.id.text_partner_null)

        var layoutProfile=view.findViewById<ConstraintLayout>(R.id.layout_profile)
        var textNickname=view.findViewById<TextView>(R.id.text_nickname)
        var textAge=view.findViewById<TextView>(R.id.text_age)
        var textDepartment=view.findViewById<TextView>(R.id.text_department)
        var textHobby=view.findViewById<TextView>(R.id.text_hobby)
        var textPersonality=view.findViewById<TextView>(R.id.text_personality)


        btnDating.setOnClickListener {
            var intent= Intent(activity,DatingActivity::class.java)
            startActivity(intent)
        }
        btnOpen.setOnClickListener {
            val intent = Intent(activity,OpenChatListActivity::class.java)
            startActivity(intent)
        }
        VolleyService.getJoinDating(UserInfo.NICKNAME,activity!!.applicationContext,{success->
            if(success==null){
                //데이팅 기능이 꺼져있는 경우
                textPartnerState.setText("만남 기능이 Off 상태입니다")
                textPartnerNull.visibility=View.VISIBLE
            }
            else{
                layoutProfile.visibility=View.VISIBLE
                if(success.getString("joined")=="false"){
                    textPartnerState.setText("랜덤 추천 상대")
                    VolleyService.datingUserReq(UserInfo.NICKNAME, UserInfo.GENDER, UserInfo.UNIV, activity!!.applicationContext, { success ->
                        var array=success

                        if(array!!.length()==0){
                            layoutProfile.visibility=View.GONE
                            textPartnerNull.visibility=View.VISIBLE
                            textPartnerNull.setText("대화 상대가 없습니다")
                        }

                        val random = Random()
                        var num=random.nextInt(array!!.length())
                        var json=array[num] as JSONObject

                        textNickname.setText(json.getString("user_nickname"))

                        //현재 연도 구하기
                        var calendar = GregorianCalendar(Locale.KOREA)
                        var year = calendar.get(Calendar.YEAR)
                        //이용자 생일 구하기
                        var birthday = json.getString("user_birthday")
                        birthday = birthday.substring(0, 4)
                        //이용자 나이 계산
                        var age = year - Integer.parseInt(birthday) + 1
                        textAge.setText(age.toString())

                        textDepartment.setText(json.getString("dept_name"))
                        textHobby.setText(json.getString("user_hobby"))
                        textPersonality.setText(json.getString("user_personality"))

                        layoutProfile.setOnClickListener {
                            val builder =
                                AlertDialog.Builder(activity!!.applicationContext!!)
                            builder.setTitle("${textNickname.text.toString()}님과의 대화")
                            builder.setMessage("시작하시겠습니까?")

                            builder.setPositiveButton("확인") { _, _ ->
                                VolleyService.createChatRoomReq(UserInfo.NICKNAME, textNickname.text.toString(),"","데이팅", UserInfo.UNIV, activity!!.applicationContext, { success ->
                                    var roomId = success!!.getString("room_id")
                                    var intent = Intent(activity!!.applicationContext, ChatActivity::class.java)
                                    intent.putExtra("room_id", roomId)
                                    intent.putExtra("title", "${UserInfo.NICKNAME}&${textNickname.text.toString()}")
                                    intent.putExtra("category","데이팅")

                                    //FCM 주제구독
                                    FirebaseMessaging.getInstance().subscribeToTopic(roomId)
                                        .addOnCompleteListener {
                                            var msg="${roomId} subscribe success"
                                            if(!it.isSuccessful) msg="${roomId} subscribe fail"
                                            Log.d("uniting","DatingAdapter.msg : ${msg}")
                                            ContextCompat.startActivity(activity!!.applicationContext, intent,null)
                                        }
                                })
                            }
                            builder.setNegativeButton("취소") { _, _ ->

                            }
                            builder.show()
                        }
                    })

                }
                else{
                    textPartnerState.setText("현재 대화중인 상대")
                }

            }
        })
        // Inflate the layout for this fragment
        return view
    }
}
