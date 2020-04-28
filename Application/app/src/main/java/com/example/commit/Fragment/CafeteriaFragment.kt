package com.example.commit.Fragment

import android.app.DownloadManager
import android.content.ContentValues.TAG
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.commit.Adapter.CafeVerticalAdapter
import com.example.commit.R
import kotlinx.android.synthetic.main.fragment_cafeteria.*
import okhttp3.Callback
import okhttp3.OkHttpClient
import org.jetbrains.anko.doAsync
import org.jsoup.Jsoup
import java.net.URL
import java.net.URLEncoder
import java.nio.charset.Charset
import java.util.*
import java.util.Base64.Decoder
import kotlin.collections.ArrayList
import android.app.Activity
import android.app.Dialog
import android.content.ContentValues.TAG
import android.os.AsyncTask
import android.os.SystemClock
import android.util.Log
//import android.support.v7.app.AppCompatActivity
//import android.support.v7.widget.LinearLayoutManager
//import android.support.v7.widget.RecyclerView
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.commit.Adapter.CafeteriaAdapter
import com.example.commit.Class.UserInfo
import com.example.commit.ListItem.*
import com.example.commit.Singleton.VolleyService
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.cafeteria_horizontal.view.*
import java.io.IOException
import okhttp3.*
import org.jetbrains.anko.doAsync
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection

class CafeteriaFragment() : Fragment() {

    lateinit var CafeVerticalRV: RecyclerView
    lateinit var CafeHorizontalRV: RecyclerView

    companion object {
        var cafetype = arrayListOf<Type>(Type("한식"), Type("중식"), Type("일식"), Type("치킨"))
        var dialog: Dialog? = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_cafeteria, container, false)
        val rootView1 = inflater.inflate(R.layout.cafeteria_horizontal, container, false)
        var sync = AsyncTask()
        sync.execute("")


        CafeHorizontalRV = rootView1.findViewById(R.id.CafeHorizontalRV)
        CafeVerticalRV = rootView.findViewById(R.id.CafeVerticalRV)

        CafeVerticalRV.setHasFixedSize(true)
        CafeVerticalRV.layoutManager = LinearLayoutManager(activity)
        var adapter = CafeVerticalAdapter(activity!!, cafetype)
        adapter.notifyDataSetChanged()
        CafeVerticalRV.adapter = adapter

        return rootView
    }

    inner class AsyncTask: android.os.AsyncTask<String, Long, Boolean>() {
        override fun onPreExecute() {
            super.onPreExecute()
            dialog = Dialog(activity, R.style.loading_dialog_style)
            var pb = ProgressBar(activity)

            dialog!!.addContentView(pb, LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT))
            dialog!!.show()
        }

        override fun doInBackground(vararg p0: String?): Boolean {
            SystemClock.sleep(1000)
            return true
        }
        override fun onProgressUpdate(vararg values: Long?) {
            super.onProgressUpdate(*values)
        }
        override fun onPostExecute(result: Boolean) {
            super.onPostExecute(result)

            dialog!!.dismiss()
        }
    }
}
