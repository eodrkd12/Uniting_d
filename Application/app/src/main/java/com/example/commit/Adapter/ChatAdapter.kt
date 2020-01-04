package com.example.commit.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.commit.Class.UserInfo
import com.example.commit.ListItem.ChatItem
import com.example.commit.R

class ChatAdapter:BaseAdapter() {
    private var chatList = ArrayList<ChatItem>()
    override fun getCount(): Int {
        return chatList.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItem(position: Int): Any {
        return chatList.get(position)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView
        val context: Context? = parent?.context

        var item=chatList[position]

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (view == null) {
            val inflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            if(item.isMyChat!!)
                view = inflater.inflate(R.layout.item_my_chat, parent, false)
            else
                view = inflater.inflate(R.layout.item_chat, parent, false)
        }

        if(!item.isMyChat!!) {
            var textSpeaker = view!!.findViewById(R.id.text_speaker) as TextView
            textSpeaker.text=item.speaker
        }
        var textContent=view!!.findViewById(R.id.text_content) as TextView
        var textTime=view.findViewById(R.id.text_time) as TextView

        textContent.text=item.content
        textTime.text=item.time

        return view
    }

    fun addItem(roomId:String, speaker: String, content: String, time:String){
        val item= ChatItem()

        if(speaker==UserInfo.NICKNAME)
            item.isMyChat = true
        else
            item.isMyChat=false

        item.roomId=roomId
        item.speaker=speaker
        item.content=content
        item.time=time
        chatList.add(item)
    }

    fun clear(){
        chatList.clear()
    }
}