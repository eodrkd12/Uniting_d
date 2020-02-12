package com.example.commit.Fragment

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.commit.ListItem.Menu

import com.example.commit.R
import kotlinx.android.synthetic.main.fragment_menu.*


class MenuFragment(val menu:ArrayList<Menu>) : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var rootView = inflater.inflate(R.layout.fragment_menu, container, false)

        var textMenu: TextView = rootView.findViewById(R.id.text_menu)
        textMenu.text = menu.get(0).name + " " + menu.get(0).price

        return rootView
    }
}
