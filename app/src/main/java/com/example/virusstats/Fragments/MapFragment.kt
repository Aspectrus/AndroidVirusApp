package com.example.virusstats.Fragments

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.virusstats.R

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_map.*

var MaxCircleSize = 1500000.0
class MapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    var activedisplayed = true
    var totaldisplayed = false
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        map_view.onCreate(savedInstanceState)
        map_view.onResume()
        map_view.getMapAsync(this)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_map, container, false)
        val  totbtn= view.findViewById<Button>(R.id.button2)
        val actbtn = view.findViewById<Button>(R.id.button)
        totbtn.setTextColor(Color.RED)
        totbtn.setOnClickListener {
                if(totaldisplayed)
                {totalformap()
                    totaldisplayed=false
                    activedisplayed = true
                    actbtn.setTextColor(Color.BLACK)
                    totbtn.setTextColor(Color.RED)
                }
            }

        actbtn.setOnClickListener {
                if(activedisplayed)
                {
                    activeformap()
                    totaldisplayed=true
                    activedisplayed = false
                    actbtn.setTextColor(Color.YELLOW)
                    totbtn.setTextColor(Color.BLACK)
                }
            }
        return view
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    override fun onMapReady(googleMap: GoogleMap) {
        super.onStart()
        mMap = googleMap
        var location:LatLng
         //Add a marker in Sydney and move the camera
      //  AllReports.sortByDescending { it.Active }
        for(key in result.keys)
        {
            if(result[key]?.Active!! == 0.0) break
            if(result[key]?.Active!! == 0.0) {
            Log.d("problem", result[key]?.Active!!.toString() + result[key]?.Country_Region)
        }
            var Lat = result[key]?.Lat
            var Long = result[key]?.Long_
            if(Lat!=null&&Long!=null) {
                location = LatLng(Lat, Long)
                mMap.addCircle(
                    CircleOptions().center(location).radius(MaxCircleSize*result[key]?.Active!!).strokeWidth(10f)
                        .strokeColor(Color.RED).fillColor(Color.argb(70, 150, 50, 50))
                )
                mMap.addMarker(
                    MarkerOptions().position(location).title(result[key]?.Country_Region)
                        .snippet("Active: " + result[key]?.Active)
                ).showInfoWindow()
            }


        }

    }

    fun activeformap()
    {
        var location:LatLng
        mMap.clear()
        for(key in result.keys)
        {
            if(result[key]?.Active!! == 0.0) break
            var Lat = result[key]?.Lat
            var Long = result[key]?.Long_
            if(Lat!=null&&Long!=null) {
                location = LatLng(Lat, Long)
                mMap.addCircle(
                    CircleOptions().center(location).radius(MaxCircleSize*result[key]?.Active!!).strokeWidth(10f)
                        .strokeColor(Color.YELLOW).fillColor(Color.argb(50,240,230,140))
                )
                mMap.addMarker(
                    MarkerOptions().position(location).title(result[key]?.Country_Region)
                        .snippet("Active: " + result[key]?.Active)
                ).showInfoWindow()
            }


        }

    }
    fun totalformap()
    {  var location:LatLng
        mMap.clear()
        for(key in result.keys)
        {
            if(result[key]?.Active!! == 0.0) break
            var Lat = result[key]?.Lat
            var Long = result[key]?.Long_
            if(Lat!=null&&Long!=null) {
                location = LatLng(Lat, Long)
                mMap.addCircle(
                    CircleOptions().center(location).radius(MaxCircleSize*result[key]?.Confirmed!!).strokeWidth(10f)
                        .strokeColor(Color.RED).fillColor(Color.argb(70, 150, 50, 50))
                )
                mMap.addMarker(
                    MarkerOptions().position(location).title(result[key]?.Country_Region)
                        .snippet("Confirmed: " + result[key]?.Confirmed)
                ).showInfoWindow()
            }
        }


    }

}
