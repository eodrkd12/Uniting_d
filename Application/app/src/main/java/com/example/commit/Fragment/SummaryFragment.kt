package com.example.commit.Fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.commit.R
import kotlinx.android.synthetic.main.fragment_summary.*

class SummaryFragment(val roadAddr:String) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var rootView = inflater.inflate(R.layout.fragment_summary, container, false)

        var textroadaddr:TextView = rootView.findViewById(R.id.text_roadaddr)

        textroadaddr.text = roadAddr
        return rootView
    }
}