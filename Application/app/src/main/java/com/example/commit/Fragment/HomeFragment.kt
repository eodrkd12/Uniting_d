package com.example.commit.Fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

import com.example.commit.MainActivity.DatingActivity
import com.example.commit.MainActivity.OpenChatListActivity
import com.example.commit.R

class HomeFragment() : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view=inflater.inflate(R.layout.fragment_home,container,false)

        var viewDating:View=view.findViewById(R.id.view_dating)
        var viewDatingClick=View.OnClickListener {
            var intent= Intent(activity,DatingActivity::class.java)
            startActivity(intent)
        }
        viewDating.setOnClickListener(viewDatingClick)

        var viewOpen:View=view.findViewById(R.id.view_open)
        var viewOpenClick = View.OnClickListener{
            val intent = Intent(activity,OpenChatListActivity::class.java)
            startActivity(intent)
        }
        viewOpen.setOnClickListener(viewOpenClick)


        var textDating: TextView=view.findViewById(R.id.text_dating)
        var textOpen: TextView=view.findViewById(R.id.text_open)

        textDating.bringToFront()
        textOpen.bringToFront()

        // Inflate the layout for this fragment
        return view
    }
}
