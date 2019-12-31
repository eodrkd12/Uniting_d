package com.example.commit.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.commit.Adapter.CafeVerticalAdapter
import com.example.commit.R

class CafeteriaFragment() : Fragment() {

    lateinit var CafeVerticalRV: RecyclerView
    lateinit var CafeHorizontalRV : RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_cafeteria, container, false)
        val rootView1 = inflater.inflate(R.layout.cafeteria_horizontal, container, false)

        CafeHorizontalRV = rootView1.findViewById(R.id.CafeHorizontalRV)
        CafeVerticalRV = rootView.findViewById(R.id.CafeVerticalRV)

        CafeVerticalRV.setHasFixedSize(true)
        CafeVerticalRV.layoutManager = LinearLayoutManager(activity)
        CafeVerticalRV.adapter = CafeVerticalAdapter(activity!!)

        return rootView
    }
}
