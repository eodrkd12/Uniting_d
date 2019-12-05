package com.example.commit.Adapter

import android.content.Context
//import android.support.v7.app.AppCompatActivity
//import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.commit.ListItem.Categoryitem
import com.example.commit.R
import kotlinx.android.synthetic.main.cafe_horizontal_item.view.*
import kotlinx.android.synthetic.main.rvitem_chat_category.view.*

class CategoryAdapter(val context: Context) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    val categorylist = arrayListOf<Categoryitem>(Categoryitem("카테고리1"), Categoryitem("카테고리2"), Categoryitem("카테고리3"), Categoryitem("카테고리4"), Categoryitem("카테고리5"), Categoryitem("카테고리6"))

    override fun getItemCount(): Int {
        return categorylist.count();
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.rvitem_chat_category, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CategoryAdapter.ViewHolder, position: Int) {
        holder.bindItems(categorylist.get(position))
        /*holder.view.setOnClickListener{
            val intent = Intent(context, InformActivity::class.java)
            context.startActivity(intent)
        }*/
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view){
        fun bindItems(data: Categoryitem) {
            itemView.btn_category.text = data.categoryname
        }
    }
}
