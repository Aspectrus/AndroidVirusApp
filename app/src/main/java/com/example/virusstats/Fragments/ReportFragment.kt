package com.example.virusstats.Fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.virusstats.R

public var result: Map<String, Report> = mapOf()
public var dateresult: Map<String, DateReport> = mapOf()
class ReportFragment : Fragment() {


    private var reportallreadyloaded = false;
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       return inflater.inflate(R.layout.fragment_report, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if(AllReports.size!=0 && !reportallreadyloaded)
        {
            val aggregate = AllReports.groupingBy(Report::Country_Region).aggregate { _, accumulator: Report?, element: Report, _ ->
                    accumulator?.let {
                        it.copy(
                            Confirmed = it.Confirmed?.plus(element.Confirmed!!),
                            Recovered = it.Recovered?.plus(element.Recovered!!),
                            Deaths = it.Deaths?.plus(element.Deaths!!),
                            Active = it.Active?.plus(element.Active!!)
                        )
                    } ?: element
                }

             result =
                aggregate.toList().sortedByDescending { (_, value) -> value.Confirmed }.toMap()
           var MaxActive:Double =0.0
            for (key in result.keys)
            {

                if(MaxActive< result[key]?.Active!!) MaxActive = result[key]?.Active!!
                val detailsTable = activity?.findViewById<TableLayout>(R.id.layout)
                val tableRow: TableRow = layoutInflater.inflate(R.layout.tablerow,null) as TableRow


                val tv =  tableRow.findViewById<TextView>(R.id.tableCell1)
                if (tv != null) {
                    tv.text=key
                }

                val tv2 =  tableRow.findViewById<TextView>(R.id.tableCell2)
                if (tv != null) {
                    tv2.text= result[key]?.Confirmed.toString().dropLast(2)
                }

                val tv3 =  tableRow.findViewById<TextView>(R.id.tableCell3)
                if (tv != null) {
                    tv3.text=result[key]?.Recovered.toString().dropLast(2)
                }

                val tv4 = tableRow.findViewById<TextView>(R.id.tableCell4)
                if (tv != null) {
                    tv4.text=result[key]?.Deaths.toString().dropLast(2)
                }

                //Add row to the table
                if (detailsTable != null) {
                    detailsTable.addView(tableRow)
                }

            }
            MaxCircleSize/=MaxActive
            reportallreadyloaded = true;
        }
    }





}
