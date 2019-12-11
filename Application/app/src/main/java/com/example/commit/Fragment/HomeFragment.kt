package com.example.commit.Fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment

import com.example.commit.MainActivity.DatingActivity
import com.example.commit.MainActivity.OpenChatListActivity
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
            var intent= Intent(activity,DatingActivity::class.java)
            startActivity(intent)
        }




        btnDating.setOnClickListener(contentBtnClick)


        var btnOpen:Button = view.findViewById(R.id.btn_open)
        var btnopenclick = View.OnClickListener{
            val intent = Intent(activity,OpenChatListActivity::class.java)
            startActivity(intent)
        }
        btnOpen.setOnClickListener(btnopenclick)


        // Inflate the layout for this fragment
        return view
    }
}
