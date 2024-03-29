package com.example.ncraftmediapost

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ncraftmediapost.adapter.postfeed.PostAdapter
import com.example.ncraftmediapost.dto.Coordinates
import com.example.ncraftmediapost.dto.Post
import com.example.ncraftmediapost.dto.PostType
import com.google.android.gms.location.*
import io.ktor.client.request.get
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.post_list_item.*
import kotlinx.android.synthetic.main.reply_list_item.*
import kotlinx.coroutines.*
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*
import android.content.Intent as Intent1
import kotlinx.android.synthetic.main.reply_list_item.latitude_text as latitude_text1
import kotlinx.android.synthetic.main.reply_list_item.longitude_text as longitude_text1


class MainActivity : AppCompatActivity(),CoroutineScope by MainScope(){

    private var mFusedLocationProviderClient: FusedLocationProviderClient? = null
    private val INTERVAL: Long = 2000
    private val FASTEST_INTERVAL: Long = 1000
    lateinit var mLastLocation: Location
    internal lateinit var mLocationRequest: LocationRequest
    private val REQUEST_PERMISSION_LOCATION = 10

    lateinit var btnStartupdate: Button
    lateinit var btnStopUpdates: Button
    lateinit var txtLat: TextView
    lateinit var txtLong: TextView
    lateinit var txtTime: TextView
    //  private var lat = ""
    // private var lon = ""

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val timeValue = System.currentTimeMillis()//timespam
        val stamp = Timestamp(timeValue)
        val date: Date = Date(stamp.getTime())
//
//        val meetup = Post(
//            1, "Netology", "First post in our network!", Coordinates(0.00, 0.00),
//            0, 0, 0,
//            "", "", "$date", true
//        )
//
//        val list = listOf(
//
//            Post(    //post
//                2, "Netology", "Kotlin", Coordinates(55.66, 00.00),
//                1, 555, 599,
//                "", "", "$date",
//                true, PostType.POST
//            ),
//            Post(
//                3, "Netology", "First REPOST in our network!", Coordinates(1.11, 9.99),
//                10, 7, 5, "",
//                "", "$date", true, PostType.REPOST
//            ),
//            Post(
//                4, "Google",
//                "Кто-то ждет дождя а кто-то - солнца. Узнай вероятность дождя на завтра спомощю Google Поиска",
//                Coordinates(0.00, 0.00),
//                2222, 111, 444,
//                "Завтра опять будет дождь ?", "", "", true, PostType.ADVERTISING
//            ),
//            Post(
//                5, "YouTube", "", Coordinates(0.00, 0.00),
//                100, 133, 222,
//                "", "", "$date", true, PostType.VIDEO
//            )
//        )
       fetchData()
        with(container) {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = PostAdapter(listOf())
        }

        mLocationRequest = LocationRequest()

        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps()
        }
    }

    fun fetchData()=launch{
        val list= withContext(Dispatchers.IO){
            Api.client.get<List<Post>>(Api.url)

        }
        Toast.makeText(this@MainActivity, "stop ${list.size}", Toast.LENGTH_SHORT).show()

    }

    override fun onDestroy() {
        super.onDestroy()
        cancel()
    }
    fun stopClick(view: View) {
        Toast.makeText(this, "stop", Toast.LENGTH_SHORT).show()
        btnStartupdate = findViewById(R.id.btn_start_upds)
        btnStopUpdates = findViewById(R.id.btn_stop_upds)
        txtTime = findViewById(R.id.time_text)
        stoplocationUpdates()
        txtTime.text = "Updates Stoped"
        btnStartupdate.isEnabled = true
        btnStopUpdates.isEnabled = false

    }

    fun startClick(view: View) {
        Toast.makeText(this, "start", Toast.LENGTH_SHORT).show()
        btnStartupdate = findViewById(R.id.btn_start_upds)
        btnStopUpdates = findViewById(R.id.btn_stop_upds)
        if (checkPermissionForLocation(this)) {
            startLocationUpdates()
            btnStartupdate.isEnabled = false
            btnStopUpdates.isEnabled = true
        }
    }

    private fun buildAlertMessageNoGps() {

        val builder = AlertDialog.Builder(this)
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
            .setCancelable(false)
            .setPositiveButton("Yes") { dialog, id ->
                startActivityForResult(
                    android.content.Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    , 11
                )
            }
            .setNegativeButton("No") { dialog, id ->
                dialog.cancel()
                finish()
            }
        val alert: AlertDialog = builder.create()
        alert.show()

        mLocationRequest = LocationRequest()

        btnStartupdate = findViewById(R.id.btn_start_upds)
        btnStopUpdates = findViewById(R.id.btn_stop_upds)
        txtLat = findViewById(R.id.latitude_text)
        txtLong = findViewById(R.id.longitude_text)
        txtTime = findViewById(R.id.time_text)

        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps()
        }

        btnStartupdate.setOnClickListener {
            if (checkPermissionForLocation(this)) {
                startLocationUpdates()
                btnStartupdate.isEnabled = false
                btnStopUpdates.isEnabled = true
            }
        }

        btnStopUpdates.setOnClickListener {
            stoplocationUpdates()
            txtTime.text = "Updates Stoped"
            btnStartupdate.isEnabled = true
            btnStopUpdates.isEnabled = false
        }

    }


    fun startLocationUpdates() {

        // Create the location request to start receiving updates

        mLocationRequest!!.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest!!.setInterval(INTERVAL)
        mLocationRequest!!.setFastestInterval(FASTEST_INTERVAL)

        // Create LocationSettingsRequest object using location request
        val builder = LocationSettingsRequest.Builder()
        builder.addLocationRequest(mLocationRequest!!)
        val locationSettingsRequest = builder.build()

        val settingsClient = LocationServices.getSettingsClient(this)
        settingsClient.checkLocationSettings(locationSettingsRequest)

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        // new Google API SDK v11 uses getFusedLocationProviderClient(this)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {


            return
        }
        mFusedLocationProviderClient!!.requestLocationUpdates(
            mLocationRequest, mLocationCallback,
            Looper.myLooper()
        )
    }

    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            // do work here
            locationResult.lastLocation
            onLocationChanged(locationResult.lastLocation)
        }
    }

    fun onLocationChanged(location: Location) {
        // New location has now been determined

        mLastLocation = location
        val date: Date = Calendar.getInstance().time
        val sdf = SimpleDateFormat("hh:mm:ss a")
        time_text.text = "Updated at : " + sdf.format(date)
        latitude_text.text = "LAT: " + mLastLocation.latitude
        longitude_text.text = "LON: " + mLastLocation.longitude

        // You can now create a LatLng Object for use with maps
    }

    fun stoplocationUpdates() {
        mFusedLocationProviderClient!!.removeLocationUpdates(mLocationCallback)
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == REQUEST_PERMISSION_LOCATION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startLocationUpdates()
                btnStartupdate.isEnabled = false
                btnStopUpdates.isEnabled = true
            } else {
                Toast.makeText(this@MainActivity, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun checkPermissionForLocation(context: Context): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED
            ) {
                true
            } else {
                // Show the permission request
                ActivityCompat.requestPermissions(
                    this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                    REQUEST_PERMISSION_LOCATION
                )
                false
            }
        } else {
            true
        }
    }

    fun transitionClick(view: View) {
        val browserIntent = Intent1(Intent1.ACTION_VIEW, Uri.parse("http://www.google.com"))
        startActivity(browserIntent)

    }


    fun playClick(view: View) {
        val browserIntent = Intent1(Intent1.ACTION_VIEW, Uri.parse("https://youtu.be/tEw7SmtRWy4"))
        startActivity(browserIntent)
    }

    fun locationByMe(view: View) {
        Toast.makeText(this, " Post $", Toast.LENGTH_SHORT).show()
        val intent = Intent1(Intent1.ACTION_VIEW, Uri.parse("geo:$latitude_text,$longitude_text"))
        startActivity(intent)
    }
}