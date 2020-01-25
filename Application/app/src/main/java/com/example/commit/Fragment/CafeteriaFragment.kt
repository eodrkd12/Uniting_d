package com.example.commit.Fragment

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.commit.Adapter.CafeVerticalAdapter
import com.example.commit.ListItem.Image
import com.example.commit.R
import kotlinx.android.synthetic.main.fragment_cafeteria.*
import org.jetbrains.anko.doAsync
import org.jsoup.Jsoup
import java.nio.charset.Charset
import java.util.*
import java.util.Base64.Decoder
import kotlin.collections.ArrayList

class CafeteriaFragment() : Fragment() {

    lateinit var CafeVerticalRV: RecyclerView
    lateinit var CafeHorizontalRV: RecyclerView

    companion object {
        var images: ArrayList<Image> = arrayListOf()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_cafeteria, container, false)
        val rootView1 = inflater.inflate(R.layout.cafeteria_horizontal, container, false)

        CafeHorizontalRV = rootView1.findViewById(R.id.CafeHorizontalRV)
        CafeVerticalRV = rootView.findViewById(R.id.CafeVerticalRV)

        /*doAsync {
            val url = "https://search.naver.com/search.naver?sm=top_hty&fbm=1&ie=utf8&query=%EA%B3%84%EB%AA%85%EB%8C%80%EC%8B%9D%EB%8B%B9"
            try {
                val doc = Jsoup.connect(url).get()
                val movieData = doc.select("div[class=list_area]").select("li")

                movieData.forEachIndexed { index, element ->


                    val mImgUrl = element.select("li div[class=thumb]").select("img").attr("src")

                    val list = Image(mImgUrl)
                    images.add(list)

                    activity!!.runOnUiThread {
                        CafeVerticalRV.setHasFixedSize(true)
                        CafeVerticalRV.layoutManager = LinearLayoutManager(activity)
                        CafeVerticalRV.adapter = CafeVerticalAdapter(activity!!)
                    }
                    Log.d(TAG, images.size.toString())

                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }*/

        CafeVerticalRV.setHasFixedSize(true)
        CafeVerticalRV.layoutManager = LinearLayoutManager(activity)
        CafeVerticalRV.adapter = CafeVerticalAdapter(activity!!)


        return rootView
    }
}
