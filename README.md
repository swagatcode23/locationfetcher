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
```
class MainActivity : AppCompatActivity(), LocationFetcherCallback{

    var locationFetcher:LocationFetcher? = null
    .
    .
```
Implement `LocationFetcherCallback` to your Activity or Fragment

Overrirde `onLocationFetched()` method:
```
 override fun onLocationFetched(latitude: Double?, longitude: Double?) {
        val lat = latitude.toString()
        val lng = longitude.toString()
        txt1?.text = "$lat , $lng"
    }
```
Initialize locationFetcher in your `onCreate()` method
```
 override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        .
        .
        '
        locationFetcher = LocationFetcher(this)
    }
```
