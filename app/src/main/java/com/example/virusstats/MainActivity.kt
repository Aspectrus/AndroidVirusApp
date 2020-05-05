package com.example.virusstats

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.virusstats.Fragments.AllReports



class MainActivity : AppCompatActivity() {

    private lateinit var homeBtn:ImageButton
    private lateinit var reportBtn:ImageButton
    private lateinit var mapBtn:ImageButton
    private lateinit var graphBtn:ImageButton

    private lateinit var mViewPager: ViewPager
    private lateinit var mPagerViewAdapter: PagerViewAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // init views
        mViewPager = findViewById(R.id.mViewPager)
        homeBtn = findViewById(R.id.homeBtn)
        reportBtn = findViewById(R.id.reportBtn)
        mapBtn = findViewById(R.id.mapBtn)
        graphBtn = findViewById(R.id.graphsBtn)
        //onclick listner

        homeBtn.setOnClickListener {
            mViewPager.currentItem = 0

        }

        reportBtn.setOnClickListener {

            mViewPager.currentItem = 1
        }

        mapBtn.setOnClickListener {
            mViewPager.currentItem = 2

        }

        graphBtn.setOnClickListener {
            mViewPager.currentItem = 3

        }

        mPagerViewAdapter = PagerViewAdapter(supportFragmentManager)
        mViewPager.adapter = mPagerViewAdapter
        mViewPager.offscreenPageLimit = 4
        mViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }
            override fun onPageSelected(position: Int) {
                changeTabs(position)
            }
            override fun onPageScrollStateChanged(state: Int) {}
        })
        mViewPager.currentItem = 0
        homeBtn.setImageResource(R.drawable.ic_home_pink)


    }

    private fun changeTabs(position: Int) {


        if (position == 0) {
            homeBtn.setImageResource(R.drawable.ic_home_pink)
            reportBtn.setImageResource(R.drawable.ic_format_align_left_black)
            mapBtn.setImageResource(R.drawable.ic_map_black)
            graphBtn.setImageResource(R.drawable.ic_graphs_black)

        }
        if (position == 1) {
            homeBtn.setImageResource(R.drawable.ic_home_black_)
            reportBtn.setImageResource(R.drawable.ic_format_align_left_red)
            mapBtn.setImageResource(R.drawable.ic_map_black)
            graphBtn.setImageResource(R.drawable.ic_graphs_black)

        }
        if (position == 2) {
            homeBtn.setImageResource(R.drawable.ic_home_black_)
            reportBtn.setImageResource(R.drawable.ic_format_align_left_black)
            mapBtn.setImageResource(R.drawable.ic_map_red)
            graphBtn.setImageResource(R.drawable.ic_graphs_black)
        }
        if (position == 3) {
            homeBtn.setImageResource(R.drawable.ic_home_black_)
            reportBtn.setImageResource(R.drawable.ic_format_align_left_black)
            mapBtn.setImageResource(R.drawable.ic_map_black)
            graphBtn.setImageResource(R.drawable.ic_graphs_red)
        }

    }



}


