package com.example.commit.Adapter

import android.content.Context
//import android.support.v7.app.AppCompatActivity
//import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.commit.ListItem.CategoryItem
import com.example.commit.R
import kotlinx.android.synthetic.main.cafe_horizontal_item.view.*

class CategoryAdapter(val context: Context, val items: ArrayList<CategoryItem>) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return items.count();
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.rvitem_chat_category, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CategoryAdapter.ViewHolder, position: Int) {
        holder.bindItems(items.get(position))
        /*holder.view.setOnClickListener{
            val intent = Intent(context, InformActivity::class.java)
            context.startActivity(intent)
        }*/
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view){
        fun bindItems(data: CategoryItem) {
            itemView.cafehorizontaltitle.text = data.category
        }
    }
}
