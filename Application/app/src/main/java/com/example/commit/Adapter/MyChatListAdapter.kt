package com.example.commit.Adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.commit.Class.UserInfo
import com.example.commit.ListItem.ChatRoomListItem
import com.example.commit.ListItem.DatingItem
import com.example.commit.MainActivity.ChatActivity
import com.example.commit.R
import com.example.commit.Singleton.VolleyService

class MyChatListAdapter(val context:Context) : RecyclerView.Adapter<MyChatListAdapter.Holder>() {
    private var chatRoomList = ArrayList<ChatRoomListItem>()

    var isMyChat:Boolean=true
    var chatIdx:Int=0;

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_chat_room_list, parent, false)
        chatIdx++
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return chatRoomList.count()
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(chatRoomList[position], context)
    }

    inner class Holder(itemView: View?): RecyclerView.ViewHolder(itemView!!){

        var textTitle=itemView?.findViewById(R.id.text_title) as TextView
        var textJoinNum=itemView?.findViewById(R.id.text_join_num) as TextView
        var viewOpen=itemView?.findViewById(R.id.view_open) as View
        fun bind(item: ChatRoomListItem, context: Context){
            textTitle.text=item.roomTitle
            textJoinNum.text="${item.curNum}/${item.limitNum}"


            viewOpen.setOnClickListener {
                var intent = Intent(context, ChatActivity::class.java)
                intent.putExtra("room_id", item.roomId)
                intent.putExtra("category", item.cateName)
                intent.putExtra("title", item.roomTitle)
                ContextCompat.startActivity(context, intent, null)
            }

            textJoinNum.bringToFront()
            textTitle.bringToFront()
        }
    }

    fun addItem(roomId: String, cateName: String, maker:String,roomTitle:String,limitNum:Int,universityName:String,curNum:Int,introduce:String){
        val item= ChatRoomListItem()

        item.roomId=roomId
        item.cateName=cateName
        item.maker=maker
        item.roomTitle=roomTitle
        item.limitNum=limitNum
        item.universityName=universityName
        item.curNum=curNum
        item.introduce=introduce

        chatRoomList.add(item)
    }

    fun clear(){
        chatRoomList.clear()
    }
}