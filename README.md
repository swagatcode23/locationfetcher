# locationfetcher
## Get continuous location points

![giphy](https://user-images.githubusercontent.com/39851751/128608159-e55ccde3-e02b-4f88-ae7b-fdaa8a559d0f.gif)

[![N|Solid](https://ethiccoders.com/ethiccoders/wp-content/uploads/2013/11/android-icon.png)](https://www.android.com/intl/en_in/)
[![N|Solid](https://deviniti.com/wp-content/uploads/2019/02/kotlin-logo.png)](https://bit.ly/3yxSFwz)

[![Generic badge](https://img.shields.io/badge/License-MIT-green.svg)](https://shields.io/) [![Generic badge](https://img.shields.io/badge/Version-1.0.2-1abc9c.svg)](https://shields.io/) 

## Add in your project
Gradle project level:
```
allprojects {
    repositories {
       .
       .
        maven { url 'https://jitpack.io' }
    }
}
```
Gradle app level:
```
dependencies {
    implementation 'com.github.swagatcode23:locationfetcher:<VERSION>'
}

```

Your Activity class:

Before adding the code in your project, you need to initialize selfcheck for ```ACCESS_FINE_LOCATION``` in code

```kotlin
class MainActivity : AppCompatActivity(), LocationFetcherCallback{
    
    companion object{
        val REQUEST_CODE_LOCATION_PERMISSION: Int = 1
    }
    
     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
       .
       .
       .
            if (ContextCompat.checkSelfPermission(applicationContext,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(
                    this@MainActivity,
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                    REQUEST_CODE_LOCATION_PERMISSION
                )
           }
        }
    }
```

Now override ```onRequestPermissionsResult``` method
```kotlin
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
```

Start adding librery component to you project
```kotlin
class MainActivity : AppCompatActivity(), LocationFetcherCallback{

    var locationFetcher:LocationFetcher? = null
    .
    .
```
Implement `LocationFetcherCallback` to your Activity or Fragment

Overrirde `onLocationFetched()` method:
```kotlin
 override fun onLocationFetched(latitude: Double?, longitude: Double?) {
        val lat = latitude.toString()
        val lng = longitude.toString()
        txt1?.text = "$lat , $lng"
    }
```
Initialize locationFetcher in your `onCreate()` method
```kotlin
 override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        .
        .
        '
        locationFetcher = LocationFetcher(this)
    }
```
Start Location Fetcher
```kotlin
val locationFetcher = LocationFetcher(this)
locationFetcher.start(this@MainActivity,R.mipmap.ic_launcher,5000,3000,"Location Fetcher title","Location Fetcher body")
```
Stop Location Fetcher
```kotlin
locationFetcher?.stop(this@MainActivity)
```

## License

```
MIT License

Copyright (c) 2021 Swagat Samal

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
