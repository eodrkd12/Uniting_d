package com.example.commit.Fragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.commit.MainActivity.ContentActivity
import com.example.commit.MainActivity.MainActivity
import com.example.commit.R
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment() : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view=inflater.inflate(R.layout.fragment_home,container,false)

        var btnDating:Button=view.findViewById(R.id.btn_dating)

        var contentBtnClick=View.OnClickListener {
            var tag=it.getTag().toString()
            var intent= Intent(activity,ContentActivity::class.java)
            intent.putExtra("tag",tag.toString())
            startActivity(intent)
        }

        btnDating.setOnClickListener(contentBtnClick)

        // Inflate the layout for this fragment
        return view
    }
}
