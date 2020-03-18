package com.example.commit.Adapter

import android.content.Context
import android.content.Intent
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.commit.Class.UserInfo
import com.example.commit.ListItem.ChatRoomListItem
import com.example.commit.ListItem.DatingItem
import com.example.commit.MainActivity.ChatActivity
import com.example.commit.R
import com.example.commit.Singleton.VolleyService

class ChatRoomListAdapter(val context:Context) : RecyclerView.Adapter<ChatRoomListAdapter.Holder>(){

    private var chatRoomList = ArrayList<ChatRoomListItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_chat_room_list, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return chatRoomList.count()
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(chatRoomList[position], context)
    }

    inner class Holder(itemView:View?): RecyclerView.ViewHolder(itemView!!){

        var textTitle=itemView?.findViewById(R.id.text_title) as TextView
        var textJoinNum=itemView?.findViewById(R.id.text_join_num) as TextView
        var viewOpen=itemView?.findViewById(R.id.view_open) as View
        fun bind(item: ChatRoomListItem, context: Context){
            textTitle.text=item.roomTitle
            textJoinNum.text="${item.curNum}/${item.limitNum}"


            viewOpen.setOnClickListener {
                VolleyService.checkJoinReq(item.roomId!!, UserInfo.NICKNAME, context, { success ->
                    if (success == "true") {
                        val builder =
                            AlertDialog.Builder(context!!)
                        builder.setTitle("참여 중인 방입니다.")

                        builder.setPositiveButton("입장") { _, _ ->
                            var intent = Intent(context, ChatActivity::class.java)
                            intent.putExtra("room_id", item.roomId)
                            intent.putExtra("category", item.cateName)
                            intent.putExtra("title",item.roomTitle)
                            startActivity(context,intent,null)
                        }
                        builder.setNegativeButton("취소") { _, _ ->

                        }
                        builder.show()
                    } else {
                        var builder=AlertDialog.Builder(context!!)
                        builder.setTitle("방에 입장하시겠습니까?")
                        builder.setMessage(item.introduce)
                        builder.setPositiveButton("입장"){_,_ ->
                            VolleyService.joinChatRoomReq(item.roomId!!, UserInfo.NICKNAME, context, { success ->
                                if (success == 1) {
                                    var isFull = item.curNum==item.limitNum

                                    if (isFull) {
                                        val builder =
                                            AlertDialog.Builder(context!!)
                                        builder.setTitle("입장할 수 없습니다.")
                                        builder.setPositiveButton("확인") { _, _ ->

                                        }
                                        builder.show()
                                    }

                                    var intent = Intent(context, ChatActivity::class.java)
                                    intent.putExtra("room_id", item.roomId)
                                    intent.putExtra("category", item.cateName)
                                    intent.putExtra("title",item.roomTitle)
                                    startActivity(context,intent,null)
                                }
                            })
                        }
                        builder.setNegativeButton("취소"){_,_ ->

                        }
                        builder.show()
                    }
                })
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

/*
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

    fun getRoomId(position: Int):String{
        return chatRoomList[position].roomId!!
    }

    fun getCategory(position: Int):String {
        return chatRoomList[position].cateName!!
    }

    fun getIntroduce(position: Int):String{
        return chatRoomList[position].introduce!!
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
}*/
