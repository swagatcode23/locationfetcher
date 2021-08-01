package io.worriorx.locfetchersample

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import io.worriorx.loc_fetcher.LocationFetcher

class MainActivity : AppCompatActivity() {

    companion object{
        val REQUEST_CODE_LOCATION_PERMISSION: Int = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
      LocationFetcher.start(this@MainActivity,R.mipmap.ic_launcher,5000,3000,"Hello","Locaaaaaaaa")
    }

    private fun stopLocationService(){
        LocationFetcher.stop(this@MainActivity)
    }
}