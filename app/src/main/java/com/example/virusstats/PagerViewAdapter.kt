package com.example.virusstats

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.virusstats.Fragments.*

class PagerViewAdapter(fm: FragmentManager?) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment? {
        return when (position) {
            0 -> {
                 HomeFragment()
            }
            1 -> {
                ReportFragment()
            }
            2 -> {
                MapFragment()
            }
            3 -> {
                GraphFragment()
            }
            else -> null
        }
    }


    override fun getCount(): Int {

        return 4
    }

}

