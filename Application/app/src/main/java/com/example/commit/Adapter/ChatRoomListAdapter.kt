package com.example.commit.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.commit.ListItem.ChatRoomListItem
import com.example.commit.R

class ChatRoomListAdapter:BaseAdapter() {
    private var chatRoomList = ArrayList<ChatRoomListItem>()

    override fun getCount(): Int {
        return chatRoomList.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItem(position: Int): Any {
        return chatRoomList.get(position)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView
        val context: Context? = parent?.context

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (view == null) {
            val inflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.item_chat_room_list, parent, false)
        }

        var textTitle=view?.findViewById(R.id.text_title) as TextView
        var textJoinNum=view.findViewById(R.id.text_join_num) as TextView

        var item=chatRoomList[position]

        textTitle.text=item.roomTitle
        textJoinNum.text="${item.curNum}/${item.limitNum}"

        return view
    }

    fun addItem(roomId: String, cateName: String, maker:String,roomTitle:String,limitNum:Int,universityName:String,curNum:Int){
        val item= ChatRoomListItem()

        item.roomId=roomId
        item.cateName=cateName
        item.maker=maker
        item.roomTitle=roomTitle
        item.limitNum=limitNum
        item.universityName=universityName
        item.curNum=curNum

        chatRoomList.add(item)
    }

    fun getRoomId(position: Int):String{
        return chatRoomList[position].roomId!!
    }

    fun getCategory(position: Int):String {
        return chatRoomList[position].cateName!!
    }

    fun isFull(position: Int):Boolean{
        var curNum=chatRoomList[position].curNum
        var maxNum=chatRoomList[position].limitNum

        if(maxNum==curNum) return true;
        else return false;
    }

    fun clear(){
        chatRoomList.clear()
    }
}