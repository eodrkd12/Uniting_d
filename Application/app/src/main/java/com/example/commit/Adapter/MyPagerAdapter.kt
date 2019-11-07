package com.example.commit.Adapter

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import com.example.commit.Fragment.AlamFragment
import com.example.commit.Fragment.ChatFragment
import com.example.commit.Fragment.HomeFragment
import com.example.commit.Fragment.OptionFragment

class MyPagerAdapter(fm : android.support.v4.app.FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return when (position) { //switch()문과 동일하다.
            0 -> {
                HomeFragment()
            }
            1 -> {
                ChatFragment()
            }
            2 -> {
                AlamFragment()
            }
            else -> {
                return OptionFragment()
            }
        }
    }

    override fun getCount(): Int {
        return 4 //4개니깐
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Home"
            1 -> "Chat"
            2 -> "Alam"
            else -> {
                return "Option"
            }
        }
    }
}

