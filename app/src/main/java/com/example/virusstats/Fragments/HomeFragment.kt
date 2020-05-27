package com.example.virusstats.Fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.virusstats.R
import com.example.virusstats.url
import com.example.virusstats.url2
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import zlc.season.rxdownload4.file
import java.util.*

var AllReports = mutableListOf<Report>()
var AllDateReports =  mutableListOf<DateReport>()
var GraphXvalues = mutableListOf<String?>()
var GraphYvalues = mutableListOf<Float?>()


data class Report(
    val FIPS: String,
    val Admin2: String,
    val Province_State: String,
    val Country_Region: String,
    val Last_Update: String,
    val Lat: Double?,
    val Long_: Double?,
    val Confirmed: Double?,
    val Deaths: Double?,
    val Recovered: Double?,
    val Active: Double?,
    val Combined_Key: String
)
data class DateReport(
    val Country_Region: String,
    val Lat: Double?,
    val Long_: Double?,
    var Confirmed: MutableList<Float?>
)

class HomeFragment() : Fragment() {
    var AllConfirmed: Long = 0
    var AllDeaths: Long = 0
    var AllRecovered: Long = 0
    val timer = Timer()
    private lateinit var Confirmed: TextView
    private lateinit var Deaths: TextView
    private lateinit var Recovered: TextView
    private lateinit var Date: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_home, container, false)

        Confirmed = view.findViewById<TextView>(R.id.Confirmed)
        Deaths = view.findViewById<TextView>(R.id.Deaths)
        Recovered = view.findViewById<TextView>(R.id.Recovered)
        Date = view.findViewById<TextView>(R.id.date)
        FirstTime()
       // timer.schedule(GetReports, 300000, 300000)
        return view
    }

    fun FirstTime()
    {

            //val file = activity?.application?.assets?.open("05-13-2020.csv")
            val file = url.file()
            val fileforgraphs = url2.file()
            val rows: List<List<String>>? = file?.let { csvReader().readAll(it) }
            val datesrows: List<List<String>>? = fileforgraphs?.let { csvReader().readAll(it) }


            if (rows != null) {

                for (row in rows.toMutableList()) {
                    val report = Report(
                        FIPS = row[0],
                        Admin2 = row[1],
                        Province_State = row[2],
                        Country_Region = row[3],
                        Last_Update = row[4],
                        Lat = row[5].toDoubleOrNull(),
                        Long_ = row[6].toDoubleOrNull(),
                        Confirmed = row[7].toDoubleOrNull(),
                        Deaths = row[8].toDoubleOrNull(),
                        Recovered = row[9].toDoubleOrNull(),
                        Active = row[10].toDoubleOrNull(),
                        Combined_Key = row[11]
                    );
                    AllReports.add(report)
                    var temp = row[7].toLongOrNull()
                    if (temp != null) AllConfirmed += temp
                    temp = row[8].toLongOrNull()
                    if (temp != null) AllDeaths += temp
                    temp = row[9].toLongOrNull()
                    if (temp != null) AllRecovered += temp

                }
            }
            if (datesrows != null) {

            for (row in datesrows.toMutableList()) {


                var datereport = DateReport(
                    Country_Region = row[1],
                    Lat = row[2].toDoubleOrNull(),
                    Long_ = row[3].toDoubleOrNull(),
                    Confirmed = mutableListOf()
                )
                if(AllDateReports.size==0) {
                    for (i in 4 until row.size) {
                        datereport.Confirmed.add(row[i].toFloatOrNull())
                        GraphXvalues.add(row[i])
                        GraphYvalues.add(0f)
                    }
                }
                else
                {
                    for (i in 4 until row.size) {
                       datereport.Confirmed.add(row[i].toFloatOrNull())
                       GraphYvalues[i-4] = GraphYvalues[i-4]?.plus(row[i].toFloatOrNull()!!)
                    }
                }

                AllDateReports.add(datereport)

            }

                val aggregate = AllDateReports.groupingBy(DateReport::Country_Region).aggregate { _, accumulator: DateReport?, element: DateReport, _ ->
                    accumulator?.let {
                        it.copy(
                            Confirmed = it.Confirmed.mapIndexed { index, xv -> xv?.plus(element.Confirmed[index]!!) } as MutableList<Float?>
                        )
                    } ?: element
                }

                dateresult =
                   aggregate.toList().sortedByDescending { (_, value) -> value.Confirmed.last() }.toMap()
        }


        AllReports.removeAt(0)
            Confirmed.text = AllConfirmed.toString()
            Deaths.text = AllDeaths.toString()
            Recovered.text = AllRecovered.toString()
        Date.text = url.file().name.dropLast(4)

    }
    val GetReports = object : TimerTask() {
        override fun run() {
            activity?.runOnUiThread(java.lang.Runnable {
                val file = activity?.application?.assets?.open("05-13-2020.csv")
                val rows: List<List<String>>? = file?.let { csvReader().readAll(it) }

                if (rows != null) {

                    for (row in rows.toMutableList()) {
                        val report = Report(
                            FIPS = row[0],
                            Admin2 = row[1],
                            Province_State = row[2],
                            Country_Region = row[3],
                            Last_Update = row[4],
                            Lat = row[5].toDoubleOrNull(),
                            Long_ = row[6].toDoubleOrNull(),
                            Confirmed = row[7].toDoubleOrNull(),
                            Deaths = row[8].toDoubleOrNull(),
                            Recovered = row[9].toDoubleOrNull(),
                            Active = row[10].toDoubleOrNull(),
                            Combined_Key = row[11]
                        );
                        AllReports.add(report)
                        var temp = row[7].toLongOrNull()
                        if (temp != null) AllConfirmed += temp
                        temp = row[8].toLongOrNull()
                        if (temp != null) AllDeaths += temp
                        temp = row[9].toLongOrNull()
                        if (temp != null) AllRecovered += temp

                    }
                }
                AllReports.removeAt(0)
                Confirmed.text = AllConfirmed.toString()
                Deaths.text = AllDeaths.toString()
                Recovered.text = AllRecovered.toString()
            }
            )
        }


    }



}


