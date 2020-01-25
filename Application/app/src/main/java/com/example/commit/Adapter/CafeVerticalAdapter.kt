package com.example.commit.Adapter

import android.app.Activity
import android.content.ContentValues.TAG
import android.os.AsyncTask
import android.util.Log
//import android.support.v7.app.AppCompatActivity
//import android.support.v7.widget.LinearLayoutManager
//import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.commit.ListItem.Homefeed
import com.example.commit.ListItem.Image
import com.example.commit.R
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.cafeteria_horizontal.view.*
import java.io.IOException
import java.net.URL
import java.net.URLEncoder
import okhttp3.*
import org.jetbrains.anko.doAsync
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection

class CafeVerticalAdapter(activity: Activity) : RecyclerView.Adapter<CafeVerticalAdapter.ViewHolder>() {
    val clientId:String = "zjmsxbzZatZyy90LhgRy"
    val clientSecret:String = "tUYfairJPI"

    val mactivity:Activity = activity

    val TAG = "Main Activity"



    override fun getItemCount():Int{
        return 3
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CafeVerticalAdapter.ViewHolder {
        val itemView : View = LayoutInflater.from(parent.context).inflate(R.layout.cafeteria_horizontal, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CafeVerticalAdapter.ViewHolder, position: Int) {
        fun fetchJson(vararg p0: String) {
            val text:String = URLEncoder.encode("계명대 맛집", "UTF-8")
            val apiURL = "https://store.naver.com/sogum/api/businesses?start=1&display=10&query=$text&sortingOrder=reviewCount"

            val url = URL(apiURL)

            val request = Request.Builder()
                .url(url)
                //.addHeader("X-Naver-Client-Id", clientId)
                //.addHeader("X-Naver-Client-Secret", clientSecret)
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

        /*var url:URL? = null
        var urlConnection: HttpURLConnection? = null
        var buf: BufferedInputStream? = null

        try {
            url = URL("https://store.naver.com/sogum/api/businesses?start=1&display=10&query=%EC%84%B1%EC%84%9C%EA%B3%84%EB%AA%85%EB%8C%80%EB%A7%9B%EC%A7%91&sortingOrder=reviewCount")
            urlConnection = url.openConnection() as HttpURLConnection
            var bufreader: BufferedReader = BufferedReader(InputStreamReader(urlConnection.getInputStream(), "UTF-8"))
            Log.d("line", bufreader.toString())

            var line: String? = null
            var page:String = ""



        }*/

    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val CafeHorizontalRV : RecyclerView = view.findViewById(R.id.CafeHorizontalRV)
        fun bindItems() {
            itemView.menutype.text = "테스트용"
        }
    }






}
