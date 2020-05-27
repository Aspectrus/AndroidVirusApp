package com.example.virusstats

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import com.tbruyelle.rxpermissions2.RxPermissions
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity


import androidx.viewpager.widget.ViewPager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup

import zlc.season.rxdownload4.download

lateinit var mPagerViewAdapter: PagerViewAdapter
var url:String = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_daily_reports"
val url2:String = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv"

class MainActivity : AppCompatActivity() {



    private lateinit var homeBtn: ImageButton
    private lateinit var reportBtn: ImageButton
    private lateinit var mapBtn: ImageButton
    private lateinit var graphBtn: ImageButton

    private lateinit var mViewPager: ViewPager



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
ContinueProgram()

    }



    fun ContinueProgram() {

        caroutine()

        startDownload(url2)
        startDownload(url)
        mViewPager = findViewById(R.id.mViewPager)
        homeBtn = findViewById(R.id.homeBtn)
        reportBtn = findViewById(R.id.reportBtn)
        mapBtn = findViewById(R.id.mapBtn)
        graphBtn = findViewById(R.id.graphsBtn)

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

    private fun startDownload(link: String) {
        var disposable = link.download()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = {
                    //  Confirmed.text = "${it.downloadSizeStr()}/${it.totalSizeStr()}"

                },
                onComplete = {
                    //tart2(url2)

                },
                onError = {
                    // Confirmed.text = "Error"
                }
            )


    }

    private fun start2(url2: String) {
        var disposable = url2.download()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = {
                    //  Confirmed.text = "${it.downloadSizeStr()}/${it.totalSizeStr()}"

                },
                onComplete = {

                },
                onError = {
                    // Confirmed.text = "Error"
                }
            )


    }
    fun caroutine() = runBlocking {

        val document = withContext(Dispatchers.IO) {
            val link:String= "https://github.com/CSSEGISandData/COVID-19/tree/master/csse_covid_19_data/csse_covid_19_daily_reports"
            val hrefs = mutableListOf<String>()
            val doc = Jsoup.connect(link).get()
            val links= doc.select("a[href\$=\".csv\"]").forEach { a ->

                hrefs.add( a.attr("href").toString())
            }
            //url = "https://github.com"+hrefs.last()
            url = "https://raw.githubusercontent.com"+ hrefs.last()
            url = url.replace("blob/","")
            println(url)
        }


    }
    @SuppressLint("CheckResult")
    private fun requestPermission(permission: String) {
        RxPermissions(this)
            .request(permission)
            .subscribe {
                if (!it) {
                    ContinueProgram()
                }
            }
    }

}





