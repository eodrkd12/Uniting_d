package com.example.commit.Adapter

import android.content.Context
import android.content.Intent
import android.provider.Settings.Global.getString
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.commit.Class.UserInfo
import com.example.commit.ListItem.DatingItem
import com.example.commit.MainActivity.ChatActivity
import com.example.commit.MainActivity.DatingActivity
import com.example.commit.R
import com.example.commit.Singleton.VolleyService
import com.google.firebase.messaging.FirebaseMessaging

class DatingAdapter(val context:Context) : RecyclerView.Adapter<DatingAdapter.Holder>(){

    private var datingList = ArrayList<DatingItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_dating_list, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return datingList.count()
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder?.bind(datingList[position], context)
    }

    inner class Holder(itemView:View?):RecyclerView.ViewHolder(itemView!!){
        var imageProfile=itemView?.findViewById(R.id.image_profile) as ImageView
        var textNickname=itemView?.findViewById(R.id.text_nickname) as TextView
        var textAge=itemView?.findViewById(R.id.text_age) as TextView
        var textDepartment=itemView?.findViewById(R.id.text_department) as TextView
        var textHobby=itemView?.findViewById(R.id.text_hobby) as TextView
        var textPersonality=itemView?.findViewById(R.id.text_personality) as TextView
        var viewProfile=itemView?.findViewById(R.id.view_profile) as View

        fun bind(item:DatingItem, context: Context){
            textNickname.text=item.nickname
            textAge.text="나이 : ${item.age}"
            textDepartment.text="학과 : ${item.department}"
            textHobby.text="취미 : ${item.hobby}"
            textPersonality.text="성격 : ${item.personality}"
            imageProfile.bringToFront()
            viewProfile.setOnClickListener {
                val builder =
                    AlertDialog.Builder(context!!)
                builder.setTitle("${item.nickname}님과의 대화")
                builder.setMessage("시작하시겠습니까?")

                builder.setPositiveButton("확인") { _, _ ->
                    VolleyService.createChatRoomReq(UserInfo.NICKNAME, item.nickname!!,"","데이팅", UserInfo.UNIV, context, { success ->
                        var roomId = success!!.getString("room_id")
                        var intent = Intent(context, ChatActivity::class.java)
                        intent.putExtra("room_id", roomId)
                        intent.putExtra("title", "${UserInfo.NICKNAME}&${item.nickname}")
                        intent.putExtra("category","데이팅")

                        //FCM 주제구독
                        FirebaseMessaging.getInstance().subscribeToTopic(roomId)
                            .addOnCompleteListener {
                                var msg="${roomId} subscribe success"
                                if(!it.isSuccessful) msg="${roomId} subscribe fail"
                                Log.d("uniting","DatingAdapter.msg : ${msg}")
                                startActivity(context,intent,null)
                            }

                        //FCM 주제구독취소
                        /*FirebaseMessaging.getInstance().unsubscribeFromTopic(roomId)
                            .addOnCompleteListener {
                                var msg="${roomId} unsubscribe success"
                                if(!it.isSuccessful) msg="${roomId} unsubscribe fail"
                                Log.d("uniting","DatingAdapter.VolleyService.createChatRoomReq msg : ${msg}")
                            }*/
                    })
                }
                builder.setNegativeButton("취소") { _, _ ->

                }
                builder.show()
            }

        }
    }

    fun addItem(nickname: String, department: String, age:Int, hobby:String, personality:String){
        val item=DatingItem()

        item.nickname=nickname
        item.department=department
        item.age=age.toString()
        item.hobby=hobby
        item.personality=personality

        datingList.add(item)
    }

    fun getNickname(position: Int): String?{
        var datingItem=datingList.get(position)

        return datingItem.nickname
    }

    fun clear(){
        datingList.clear()
    }
}


/*
class ChatAdapter : BaseAdapter() {

    private var datingList = ArrayList<DatingItem>()

    override fun getCount(): Int {
        return datingList.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItem(position: Int): Any {
        return datingList.get(position)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView
        val context:Context? = parent?.context

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (view == null) {
            val inflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.item_dating_list, parent, false)
        }

        var textNickname=view?.findViewById(R.id.text_nickname) as TextView
        var textDepartment=view.findViewById(R.id.text_department) as TextView
        var textAge=view.findViewById(R.id.text_age) as TextView
        var textHobby=view.findViewById(R.id.text_hobby) as TextView
        var textPersonality=view.findViewById(R.id.text_personality) as TextView


        var item=datingList[position]

        textNickname.setText(item.nickname)
        textDepartment.setText("학과 : ${item.department}")
        textAge.setText("나이 : ${item.age}살")
        textHobby.setText("취미 : ${item.hobby}")
        textPersonality.setText("성격 : ${item.personality}")

        var viewProfile=view.findViewById(R.id.view_profile) as View
        viewProfile.setOnClickListener {
            val builder =
                AlertDialog.Builder(context!!)
            builder.setTitle("${item.nickname}님과의 대화")
            builder.setMessage("시작하시겠습니까?")

            builder.setPositiveButton("확인") { _, _ ->
                VolleyService.createChatRoomReq(UserInfo.NICKNAME, item.nickname!!,"","데이팅", UserInfo.UNIV, context, { success ->
                    var roomId = success!!.getString("room_id")
                    var intent = Intent(context, ChatActivity::class.java)
                    intent.putExtra("room_id", roomId)
                    intent.putExtra("category","데이팅")
                    startActivity(context,intent,null)
                })
            }
            builder.setNegativeButton("취소") { _, _ ->

            }
            builder.show()
        }

        var imageProfile=view.findViewById(R.id.image_profile) as ImageView
//        imageProfile.bringToFront()

        return view
    }

    fun addItem(nickname: String, department: String, age:Int, hobby:String, personality:String){
        val item=DatingItem()

        item.nickname=nickname
        item.department=department
        item.age=age.toString()
        item.hobby=hobby
        item.personality=personality

        datingList.add(item)
    }

    fun getNickname(position: Int): String?{
        var datingItem=datingList.get(position)

        return datingItem.nickname
    }

    fun clear(){
        datingList.clear()
    }
}*/
