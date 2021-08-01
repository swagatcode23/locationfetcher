package io.worriorx.loc_fetcher

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService

class LocationFetcher {
    companion object{

        private fun isLocationFetcherRunning(context: Context):Boolean{
            val activityManager: ActivityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            if (activityManager != null){
                for (service: ActivityManager.RunningServiceInfo in activityManager.getRunningServices(Int.MAX_VALUE)){
                    if (LocationFetchService::class.java.name.equals(service.service.className)){
                        if (service.foreground){
                            return true
                        }
                    }
                }
                return false
            }
            return false
        }


        fun start(context: Context,icon:Int){
            if (!isLocationFetcherRunning(context)){
                val intent = Intent(context,LocationFetchService::class.java)
                intent.setAction(Constants.ACTION_START_LOCATION_SERVICE)
                intent.putExtra("SMALLICON", icon)
                context.startService(intent)
            }
        }

        fun start(context: Context,icon:Int,interval:Long){
            if (!isLocationFetcherRunning(context)){
                val intent = Intent(context,LocationFetchService::class.java)
                intent.setAction(Constants.ACTION_START_LOCATION_SERVICE)
                intent.putExtra("SMALLICON", icon);
                intent.putExtra("INTERVAL", interval)
                context.startService(intent)
            }
        }


        fun start(context: Context,icon:Int,interval:Long,fast_interval:Long){
            if (!isLocationFetcherRunning(context)){
                val intent = Intent(context,LocationFetchService::class.java)
                intent.setAction(Constants.ACTION_START_LOCATION_SERVICE)
                intent.putExtra("SMALLICON", icon);
                intent.putExtra("INTERVAL", interval)
                intent.putExtra("FAST_INTERVAL", fast_interval)
                context.startService(intent)
            }
        }

        fun start(context: Context,icon:Int,title:String,desciption:String){
            if (!isLocationFetcherRunning(context)){
                val intent = Intent(context,LocationFetchService::class.java)
                intent.setAction(Constants.ACTION_START_LOCATION_SERVICE)
                intent.putExtra("SMALLICON", icon)
                intent.putExtra("TITLE", title)
                intent.putExtra("DESCRIPTION", desciption)
                context.startService(intent)
            }
        }

        fun start(context: Context,icon:Int,interval:Long,fast_interval:Long,title:String,desciption:String){
            if (!isLocationFetcherRunning(context)){
                val intent = Intent(context,LocationFetchService::class.java)
                intent.setAction(Constants.ACTION_START_LOCATION_SERVICE)
                intent.putExtra("SMALLICON", icon)
                intent.putExtra("TITLE", title)
                intent.putExtra("DESCRIPTION", desciption)
                intent.putExtra("INTERVAL", interval)
                intent.putExtra("FAST_INTERVAL", fast_interval)
                context.startService(intent)
            }
        }


        fun stop(context: Context){
            if (isLocationFetcherRunning(context)){
                val intent = Intent(context,LocationFetchService::class.java)
                intent.setAction(Constants.ACTION_STOP_LOCATION_SERVICE)
                context.startService(intent)
            }
        }

    }
}