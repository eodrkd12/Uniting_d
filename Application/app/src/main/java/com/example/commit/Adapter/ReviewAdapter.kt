package com.example.commit.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.commit.ListItem.ReviewItem
import com.example.commit.R
import kotlinx.android.synthetic.main.item_review.view.*

class ReviewAdapter(val reviewList: ArrayList<ReviewItem>): RecyclerView.Adapter<ReviewAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return reviewList.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_review, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ReviewAdapter.ViewHolder, position: Int) {
        holder.itemView.reviewnickname.text = reviewList.get(position).nickname
        holder.itemView.reviewpoint.text = reviewList.get(position).point.toString()
        holder.itemView.reviewcontent.text = reviewList.get(position).content
    }


    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view){

    }

}