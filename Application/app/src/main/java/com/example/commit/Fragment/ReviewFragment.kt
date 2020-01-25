package com.example.commit.Fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.commit.Adapter.ReviewAdapter
import com.example.commit.Class.UserInfo
import com.example.commit.ListItem.ReviewItem

import com.example.commit.R
import com.example.commit.Singleton.VolleyService
import kotlinx.android.synthetic.main.fragment_review.*
import kotlinx.coroutines.delay
import org.json.JSONArray
import org.json.JSONObject


class ReviewFragment(val name: String) : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var reviewArray: JSONArray? = null
        var reviewList: ArrayList<ReviewItem> = arrayListOf()
        //var resource: Int? = null

        VolleyService.getReviewReq(name, UserInfo.UNIV, activity!!, { success ->
            reviewArray = success

            if (reviewArray!!.length() == 0) {

            } else {
                for (i in 0..reviewArray!!.length() - 1) {
                    var json = JSONObject()
                    json = reviewArray!![i] as JSONObject

                    var nickname = json.getString("user_nickname")
                    var date = json.getString("date")
                    var point = json.getInt("point")
                    var content = json.getString("content")

                    reviewList.add(ReviewItem(nickname, date, point, content))
                }


                reviewRV.setHasFixedSize(true)
                reviewRV.layoutManager = LinearLayoutManager(activity)
                reviewRV.adapter = ReviewAdapter(reviewList)

            }
        })


        if(reviewArray!!.length() == 0)
        {
            return inflater.inflate(R.layout.fragment_noreview, container, false)
        }
        else
        {
            return inflater.inflate(R.layout.fragment_review, container, false)
        }
    }
}
