package com.example.virusstats.Fragments

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.virusstats.R
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import kotlinx.android.synthetic.main.fragment_graphs.*
import android.widget.Toast
import com.example.virusstats.CustomMarker
import kotlin.math.roundToLong


class GraphFragment : Fragment() {
    lateinit var textView:AutoCompleteTextView
    lateinit var v:View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_graphs, container, false)
        textView = v.findViewById<AutoCompleteTextView>(R.id.autoCompleteTextView2)
        return v
    }

    override fun onStart() {
        super.onStart()

        var suggestion = dateresult.keys.toTypedArray()
        var adapter =ArrayAdapter(v.context, android.R.layout.simple_list_item_1,suggestion)
        textView.setAdapter(adapter)

        textView.setOnFocusChangeListener( {v, b->if(b) autoCompleteTextView2.showDropDown()})
        textView.onItemClickListener = AdapterView.OnItemClickListener{
                parent,view,position,id->
            val selectedItem = parent.getItemAtPosition(position).toString()
            drawgraphs(selectedItem)
            v.hideKeyboard()
        }

            val entries = ArrayList<Entry>()
            val barentries = ArrayList<BarEntry>()

            var index: Float = 0f
            for (entry in GraphYvalues) {
                if (entry != null) {
                    val x: Float = entry
                    entries.add(Entry(index, entry))
                    index += 1f
                }

            }


            val vl = LineDataSet(entries, "Total Cases")
            val xAxis: XAxis = lineChart.xAxis
            xAxis.valueFormatter = DateValueFormatter()

            vl.setDrawFilled(false)
            vl.valueTextSize = 12f
            vl.valueTextColor = R.color.yellow
            vl.lineWidth = 1f
            vl.fillColor = R.color.trans_green
            vl.fillAlpha = R.color.trans_red
            vl.circleRadius = 5f
            vl.setCircleColor(Color.YELLOW)
            vl.setColor(Color.YELLOW)
            vl.setDrawValues(false)
            lineChart.data = LineData(vl)

            lineChart.axisRight.isEnabled = false


            lineChart.setTouchEnabled(true)
            lineChart.setPinchZoom(true)
            lineChart.setScaleEnabled(true);

            lineChart.description.text = "Days"


            lineChart.animateX(1800, Easing.EaseInExpo)
     lineChart.isHighlightPerTapEnabled = true
        /*
     lineChart.setOnChartValueSelectedListener(object : OnChartValueSelectedListener {

         override fun onNothingSelected() {}

         override fun onValueSelected(e: Entry, h: Highlight) {
             val num: String = e.y.toString().dropLast(2)
             val yearWeek:String = GraphXvalues[e.x.toInt()].toString()
             Toast.makeText(context, "Cases: $num Date: $yearWeek", Toast.LENGTH_SHORT).show()
         }
     })
*/
        val markerView = context?.let { CustomMarker(it, R.layout.marker_view) }
        lineChart.marker = markerView

            index = 0F
            for (i in 1 until GraphYvalues.size - 1) {

                val value: Float? = GraphYvalues[i]?.minus(GraphYvalues[i - 1]!!)
                if (value != null) {
                    barentries.add(BarEntry(index, value))
                }
                index += 1f
            }
            val vlbar = BarDataSet(barentries, "Daily Cases")
            val xAxisbar: XAxis = barChart.xAxis
            xAxisbar.valueFormatter = DateValueFormatter()


            vlbar.valueTextSize = 12f
            vlbar.valueTextColor = R.color.trans_yellow
            vlbar.barBorderWidth = 1f
            vlbar.setDrawValues(false)
            vlbar.barBorderColor = R.color.trans_yellow
            vlbar.setColor(Color.YELLOW)

            barChart.data = BarData(vlbar)
        val markerView2 = context?.let { CustomMarker(it, R.layout.marker_view) }
        barChart.marker = markerView2

            barChart.axisRight.isEnabled = false


        }
    class DateValueFormatter: ValueFormatter()  {
        override fun getFormattedValue(value: Float): String {
            return GraphXvalues[value.toInt()].toString()
        }
    }

    fun drawgraphs(key:String)
    {



        val entries = ArrayList<Entry>()
        val barentries = ArrayList<BarEntry>()

        var index: Float = 0f
        for (entry in dateresult[key]?.Confirmed!!) {
            if (entry != null) {
                val x: Float = entry
                entries.add(Entry(index, entry))
                index += 1f
            }

        }



        val vl = LineDataSet(entries, "Total Cases")
        val xAxis: XAxis = lineChart.xAxis
        xAxis.valueFormatter = DateValueFormatter()

        vl.setDrawFilled(false)
        vl.valueTextSize = 12f
        vl.valueTextColor = R.color.yellow
        vl.lineWidth = 1f
        vl.fillColor = R.color.trans_green
        vl.fillAlpha = R.color.trans_red
        vl.circleRadius = 5f
        vl.setCircleColor(Color.YELLOW)
        vl.setColor(Color.YELLOW)
        vl.setDrawValues(false)

        lineChart.data = LineData(vl)

        lineChart.axisRight.isEnabled = false



        lineChart.setTouchEnabled(true)
        lineChart.setPinchZoom(true)


        lineChart.description.text = "Days"


        lineChart.animateX(1800, Easing.EaseInExpo)



        index = 0F
        for (i in 1 until dateresult[key]?.Confirmed!!.size - 1) {

            val value: Float? = dateresult[key]?.Confirmed!![i]?.minus(dateresult[key]?.Confirmed!![i - 1]!!)
            if (value != null) {
                barentries.add(BarEntry(index, value))
            }
            index += 1f
        }
        val vlbar = BarDataSet(barentries, "Daily Cases")

        val xAxisbar: XAxis = barChart.xAxis
        xAxisbar.valueFormatter = DateValueFormatter()


        vlbar.valueTextSize = 12f
        vlbar.valueTextColor = R.color.trans_yellow
        vlbar.barBorderWidth = 1f
        vlbar.setDrawValues(false)
        vlbar.barBorderColor = R.color.trans_yellow
        vlbar.setColor(Color.YELLOW)

        barChart.data = BarData(vlbar)

        barChart.setTouchEnabled(true)
        barChart.setPinchZoom(true)
        barChart.animateX(1800, Easing.EaseInExpo)

       barChart.axisRight.isEnabled = false
       val markerView2 = context?.let { CustomMarker(it, R.layout.marker_view) }
        barChart.marker = markerView2

    }
    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }


}
