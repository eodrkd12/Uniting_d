package com.example.commit.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.commit.ListItem.Homefeed
import com.example.commit.MainActivity.InformActivity
import com.example.commit.R
import kotlinx.android.synthetic.main.cafeteria_item.view.*

class CafeteriaAdapter(val context: Context, val homefeed: Homefeed) : RecyclerView.Adapter<CafeteriaAdapter.ViewHolder>() {
    override fun getItemCount(): Int {
        return homefeed.items.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CafeteriaAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.cafeteria_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CafeteriaAdapter.ViewHolder, position: Int) {
        var temp=homefeed.items.get(position).name
        var test : String? = ""
        var title:String?=""
        for(i in 0..temp!!.length-1){
            if(temp[i]=='<'||temp[i]=='>'||temp[i]=='/'||temp[i]=='b')
                continue
            else
                title+=temp[i]
        }
        holder.itemView.cafeteriatitle.text = title

        if(homefeed.items.get(position).tags == null)
        {
            holder.itemView.textView6.text = "정보없음"
        }
        else
        {
            for(i in 0..homefeed.items.get(position).tags!!.size-1)
            {
                test += "#" + homefeed.items.get(position).tags!!.get(i) + " "
            }
            holder.itemView.textView6.text = test
        }


        Glide.with(holder.itemView)
            .load(homefeed.items.get(position).imageSrc)
            .override(300,400)
            .into(holder.itemView.cafeteriaimage)

        holder.view.setOnClickListener{
            val intent = Intent(context, InformActivity::class.java)
            intent.putExtra("name",homefeed.items.get(position).name)
            intent.putExtra("x", homefeed.items.get(position).x)
            intent.putExtra("y", homefeed.items.get(position).y)
            intent.putExtra("phone", homefeed.items.get(position).phone)
            intent.putExtra("id", homefeed.items.get(position).id)
            intent.putExtra("roadAddr", homefeed.items.get(position).roadAddr)
            context.startActivity(intent)
        }


    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view){
    }
}