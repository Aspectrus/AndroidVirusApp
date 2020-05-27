package com.example.virusstats
import android.content.Context
import com.example.virusstats.Fragments.GraphXvalues
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF
import kotlinx.android.synthetic.main.marker_view.view.*

class CustomMarker(context: Context, layoutResource: Int):  MarkerView(context, layoutResource) {
    override fun refreshContent(entry: Entry?, highlight: Highlight?) {
        val value = entry?.y?.toDouble() ?: 0.0
        val date = entry?.x?.toDouble() ?: 0.0
        val yearWeek:String = GraphXvalues[x?.toInt()].toString()
        var resText = ""
        if(value.toString().length > 8){
            resText = "Cases: " + value.toString().substring(0,7)+" Date: "  + yearWeek
        }
        else{
            resText = "Cases: " + value.toString()+" Date: "  + yearWeek
        }
        markertext.text = resText
        super.refreshContent(entry, highlight)
    }

    override fun getOffsetForDrawingAtPoint(xpos: Float, ypos: Float): MPPointF {
        return MPPointF(-width / 2f, -height - 10f)
    }
}
