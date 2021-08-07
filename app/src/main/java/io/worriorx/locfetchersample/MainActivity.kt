package io.worriorx.locfetchersample

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import io.worriorx.loc_fetcher.LocationFetcher
import io.worriorx.loc_fetcher.LocationFetcherCallback

class MainActivity : AppCompatActivity(), LocationFetcherCallback{

    var locationFetcher:LocationFetcher? = null

    var txt1: TextView? = null

    companion object{
        val REQUEST_CODE_LOCATION_PERMISSION: Int = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        locationFetcher = LocationFetcher(this)
        txt1 = findViewById(R.id.textView2)
        findViewById<Button>(R.id.mainActivityBtnStart).setOnClickListener {
            if (ContextCompat.checkSelfPermission(applicationContext,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(
                    this@MainActivity,
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                    REQUEST_CODE_LOCATION_PERMISSION
                )
            }else{
                startLocationService()
            }
        }
        findViewById<Button>(R.id.mainActivityBtnStop).setOnClickListener {
            stopLocationService()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_LOCATION_PERMISSION && grantResults.size > 0){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                startLocationService()
            }else{
                Toast.makeText(applicationContext,"Permission denied!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun startLocationService(){
        val locationFetcher = LocationFetcher(this)
      locationFetcher.start(this@MainActivity,R.mipmap.ic_launcher,5000,3000,"Location Fetcher title","Location Fetcher body")
    }

    private fun stopLocationService(){
        locationFetcher?.stop(this@MainActivity)
    }

    override fun onLocationFetched(latitude: Double?, longitude: Double?) {
        val lat = latitude.toString()
        val lng = longitude.toString()
        txt1?.text = "$lat , $lng"
    }


}