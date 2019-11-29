package com.example.commit.Fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.commit.MainActivity.DatingActivity
import com.example.commit.R

class HomeFragment() : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view=inflater.inflate(R.layout.fragment_home,container,false)

        var btnDating:Button=view.findViewById(R.id.btn_dating)

        var contentBtnClick=View.OnClickListener {
            var tag=it.getTag().toString()
            var intent= Intent(activity,DatingActivity::class.java)
            intent.putExtra("tag",tag.toString())
            startActivity(intent)
        }

        btnDating.setOnClickListener(contentBtnClick)

        // Inflate the layout for this fragment
        return view
    }
}
