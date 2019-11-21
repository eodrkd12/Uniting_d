package com.example.commit.Adapter

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.commit.ListItem.Homefeed
import com.example.commit.R
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.cafeteria_horizontal.view.*
import java.io.IOException
import java.net.URL
import java.net.URLEncoder
import okhttp3.*

class CafeVerticalAdapter(activity: Activity) : RecyclerView.Adapter<CafeVerticalAdapter.ViewHolder>() {
    val clientId:String = "zjmsxbzZatZyy90LhgRy"
    val clientSecret:String = "tUYfairJPI"

    val mactivity:Activity = activity

    override fun getItemCount():Int{
        return 3
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CafeVerticalAdapter.ViewHolder {
        val itemView : View = LayoutInflater.from(parent.context).inflate(R.layout.cafeteria_horizontal, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CafeVerticalAdapter.ViewHolder, position: Int) {
        fun fetchJson(vararg p0: String) {
            val text:String = URLEncoder.encode("성당동 식당", "UTF-8")
            val apiURL = "https://openapi.naver.com/v1/search/local.json?query=$text&display=30&start=2"

            val url = URL(apiURL)

            val request = Request.Builder()
                .url(url)
                .addHeader("X-Naver-Client-Id", clientId)
                .addHeader("X-Naver-Client-Secret", clientSecret)
                .method("GET", null)
                .build()

            val client = OkHttpClient()
            client.newCall(request).enqueue(object: Callback {
                override fun onResponse(call: Call?, response: Response?) {

                    mactivity.runOnUiThread {
                        val body = response?.body()?.string()
                        println("Success to execute request : $body")

                        val gson = GsonBuilder().create()

                        val homefeed = gson.fromJson(body, Homefeed::class.java)

                        holder.CafeHorizontalRV.setHasFixedSize(true)
                        holder.CafeHorizontalRV.layoutManager = LinearLayoutManager(mactivity, LinearLayout.HORIZONTAL, false)
                        holder.CafeHorizontalRV.adapter = CafeHorizontalAdapter(mactivity!!, homefeed)
                    }
                }

                override fun onFailure(call: Call?, e: IOException?) {
                    println("Failed to execute request")
                }
            })
        }
        holder.bindItems()
        fetchJson(" ")
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val CafeHorizontalRV : RecyclerView = view.findViewById(R.id.CafeHorizontalRV)
        fun bindItems() {
            itemView.menutype.text = "테스트용"
        }
    }

}
