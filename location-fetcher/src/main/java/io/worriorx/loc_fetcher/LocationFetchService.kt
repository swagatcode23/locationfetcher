package io.worriorx.loc_fetcher

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.*
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices


class LocationFetchService: Service() {

    var data: Messenger? = null
    private var icon:Int? = null
    private var title: String? = "Location Fetcher"
    private var description: String? = "Running"
    private var loc_interval: Long = 4000
    private var loc_fast_interval: Long = 2000

    var latitude: Double = 0.0
    var longitude: Double = 0.0

    private val locationCallback: LocationCallback = object : LocationCallback(){
        override fun onLocationResult(locationResult: LocationResult) {
            super.onLocationResult(locationResult)
            if (locationResult.lastLocation != null){
                latitude = locationResult.lastLocation.latitude
                longitude = locationResult.lastLocation.longitude
                val fetchedPoints: Message = Message.obtain()
                val bundle = Bundle()
                bundle.putDouble("result_lat", latitude)
                bundle.putDouble("result_long", longitude)
                fetchedPoints.setData(bundle)
                try {
                    data!!.send(fetchedPoints)
                } catch (e: RemoteException) {
                    e.printStackTrace()
                }
            }
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        throw UnsupportedOperationException("Not yet implemented ")
    }

    private fun startLocationService(){
        val channelId = "location_notification_channel"
        val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val resutlIntent =  Intent()
        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            applicationContext,
            0,
            resutlIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val builder: NotificationCompat.Builder = NotificationCompat.Builder(
            applicationContext, channelId
        )
        builder.setSmallIcon(icon!!)
        builder.setContentTitle(title)
        builder.setDefaults(NotificationCompat.DEFAULT_ALL)
        builder.setContentText(description)
        builder.setContentIntent(pendingIntent)
        builder.setAutoCancel(false)
        builder.setPriority(NotificationCompat.PRIORITY_MAX)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            if (notificationManager != null &&
                notificationManager.getNotificationChannel(channelId) == null){
                val notificationChannel = NotificationChannel(
                    channelId,
                    "Location Service",
                    NotificationManager.IMPORTANCE_HIGH
                )
                notificationChannel.description = "this channel is used by location service"
                notificationManager.createNotificationChannel(notificationChannel)
            }
        }

        val locationRequest = LocationRequest.create().apply {
            interval = loc_interval
            fastestInterval = loc_fast_interval
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        //permission check before requesting fusedlocation service
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED) { return }

        LocationServices.getFusedLocationProviderClient(this)
            .requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())

        startForeground(Constants.LOCATION_SERVICE_ID, builder.build())
    }

    private fun stopLocationService(){
        LocationServices.getFusedLocationProviderClient(this)
            .removeLocationUpdates(locationCallback)
        stopForeground(true)
        stopSelf()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent != null){
            val action = intent.action
            if (action != null){
                if (action.equals(Constants.ACTION_START_LOCATION_SERVICE)){
                    data = intent.getParcelableExtra("messenger");
                    loc_fast_interval = intent.getLongExtra("FAST_INTERVAL", 2000)
                    loc_interval = intent.getLongExtra("INTERVAL", 4000)
                    icon = intent.getIntExtra("SMALLICON", 0)
                    title = intent.getStringExtra("TITLE")
                    description = intent.getStringExtra("DESCRIPTION")
                    startLocationService()
                }else if(action.equals(Constants.ACTION_STOP_LOCATION_SERVICE)){
                    stopLocationService()
                }
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }
}