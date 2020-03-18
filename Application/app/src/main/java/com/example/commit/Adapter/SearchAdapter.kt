package com.example.commit.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.commit.ListItem.ChatRoomListItem
import com.example.commit.R
import kotlinx.android.synthetic.main.item_chat_room_list.view.*


class SearchAdapter(val context: Context, val openchatFilter:ArrayList<ChatRoomListItem>) : RecyclerView.Adapter<SearchAdapter.ViewHolder>() {
    override fun getItemCount(): Int {
        return openchatFilter.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_chat_room_list, parent, false)
        return ViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: SearchAdapter.ViewHolder, position: Int) {
       //여기 무엇을 넣으면 될까요????
        holder.itemView.text_title.text=openchatFilter.get(position).roomTitle
        holder.itemView.text_join_num.text=openchatFilter.get(position).curNum.toString()+"/"+openchatFilter.get(position).limitNum.toString()
    }


    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    }
}