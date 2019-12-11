package com.example.commit.Adapter

import android.content.Context
import android.content.Intent
//import android.support.v7.app.AppCompatActivity
//import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.commit.ListItem.Homefeed
import com.example.commit.ListItem.Item
import com.example.commit.R
import kotlinx.android.synthetic.main.cafe_horizontal_item.view.*

class CafeHorizontalAdapter(val context: Context, val homefeed: Homefeed) : RecyclerView.Adapter<CafeHorizontalAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return homefeed.items.count();
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CafeHorizontalAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.cafe_horizontal_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CafeHorizontalAdapter.ViewHolder, position: Int) {
        holder.bindItems(homefeed.items.get(position))
        /*holder.view.setOnClickListener{
            val intent = Intent(context, InformActivity::class.java)
            context.startActivity(intent)
        }*/

    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view){
        fun bindItems(data: Item) {
            itemView.cafehorizontaltitle.text = data.title
        }
    }
}
