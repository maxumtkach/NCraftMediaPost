package com.example.ncraftmediapost.adapter.postfeed

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.os.Looper
import android.provider.Settings
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.ncraftmediapost.MainActivity
import com.example.ncraftmediapost.R
import com.example.ncraftmediapost.dto.Post
import com.google.android.gms.location.*
import kotlinx.android.synthetic.main.post_list_item.*
import kotlinx.android.synthetic.main.post_list_item.view.*
import kotlinx.android.synthetic.main.post_list_item.view.author_text
import kotlinx.android.synthetic.main.post_list_item.view.btn_image_chat
import kotlinx.android.synthetic.main.post_list_item.view.btn_image_like
import kotlinx.android.synthetic.main.post_list_item.view.btn_location
import kotlinx.android.synthetic.main.post_list_item.view.chat_text
import kotlinx.android.synthetic.main.post_list_item.view.data_text
import kotlinx.android.synthetic.main.post_list_item.view.like_text
import kotlinx.android.synthetic.main.post_list_item.view.post_text
import kotlinx.android.synthetic.main.post_list_item.view.share_btn
import kotlinx.android.synthetic.main.post_list_item.view.share_text
import kotlinx.android.synthetic.main.reply_list_item.*
import kotlinx.android.synthetic.main.reply_list_item.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlinx.android.synthetic.main.post_list_item.view.latitude_text as latitude_text1
import kotlinx.android.synthetic.main.post_list_item.view.longitude_text as longitude_text1

class PostViewHolder(adapter: PostAdapter, view: View) : BaseViewHolder(adapter, view) {

    private var countLike: Int = 111    // счетчики
    private var countChat: Int = 140
    private var countShare: Int = 74
    private var lat = ""
    private var lon = ""


    init {
      //  mLocationRequest = LocationRequest()

        with(itemView) {
            btn_image_like.setOnClickListener {
                //LIKE

                if (adapterPosition != RecyclerView.NO_POSITION) {
                    Toast.makeText(context, "like", Toast.LENGTH_SHORT).show()

                    btn_image_like.setImageResource(R.drawable.ic_favorite_red_24dp)
                    countLike++
                    like_text.text = countLike.toString()
                    like_text.setTextColor(Color.rgb(255, 0, 0))
                }
            }

            btn_image_chat.setOnClickListener {
                //CHAT
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    Toast.makeText(context, "shat", Toast.LENGTH_SHORT).show()
                    btn_image_chat.setImageResource(R.drawable.ic_chat_bubble_24dp)
                    countChat++
                    chat_text.text = countChat.toString()
                    chat_text.setTextColor(Color.rgb(255, 0, 0))
                }
            }

            share_btn.setOnClickListener {
                //SHARE
                Toast.makeText(context, "share", Toast.LENGTH_SHORT).show()

                share_btn.setImageResource(R.drawable.ic_share_24dp)
                countShare++
                share_text.text = countShare.toString()
            }

//            btn_start_upds.setOnClickListener() {
//
//                if (adapterPosition != RecyclerView.NO_POSITION)
//                    Toast.makeText(context, "STart+", Toast.LENGTH_SHORT).show()
////                bbb.v()
//
//                btnStartupdate = findViewById(R.id.btn_start_upds)
//                btnStopUpdates = findViewById(R.id.btn_stop_upds)
//                if (checkPermissionForLocation(this)) {
//                    startLocationUpdates()
//                    btnStartupdate.isEnabled = false
//                    btnStopUpdates.isEnabled = true
//                }
            }

        }
//        private fun buildAlertMessageNoGps() {
//
//            val builder = AlertDialog.Builder(this)
//            builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
//                .setCancelable(false)
//                .setPositiveButton("Yes") { dialog, id ->
//                    startActivityForResult(
//                        android.content.Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
//                        , 11
//                    )
//                }
//                .setNegativeButton("No") { dialog, id ->
//                    dialog.cancel()
//                    finish()
//                }
//            val alert: AlertDialog = builder.create()
//            alert.show()
//
//            mLocationRequest = LocationRequest()
//
//            btnStartupdate = findViewById(R.id.btn_start_upds)
//            btnStopUpdates = findViewById(R.id.btn_stop_upds)
//            txtLat = findViewById(R.id.latitude_text)
//            txtLong = findViewById(R.id.longitude_text)
//            txtTime = findViewById(R.id.time_text)
//
//            val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
//            if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
//                buildAlertMessageNoGps()
//            }
//
//            btnStartupdate.setOnClickListener {
//                if (checkPermissionForLocation(this)) {
//                    startLocationUpdates()
//                    btnStartupdate.isEnabled = false
//                    btnStopUpdates.isEnabled = true
//                }
//            }
//
//            btnStopUpdates.setOnClickListener {
//                stoplocationUpdates()
//                txtTime.text = "Updates Stoped"
//                btnStartupdate.isEnabled = true
//                btnStopUpdates.isEnabled = false
//            }
//
//        }
//
//        override fun onRequestPermissionsResult(
//            requestCode: Int,
//            permissions: Array<out String>,
//            grantResults: IntArray
//        ) {
//            if (requestCode == REQUEST_PERMISSION_LOCATION) {
//                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    startLocationUpdates()
//                    btnStartupdate.isEnabled = false
//                    btnStopUpdates.isEnabled = true
//                } else {
//                    Toast.makeText(this@MainActivity, "Permission Denied", Toast.LENGTH_SHORT).show()
//                }
//            }
//        }
//
//        fun checkPermissionForLocation(context: Context): Boolean {
//            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//
//                if (context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) ==
//                    PackageManager.PERMISSION_GRANTED
//                ) {
//                    true
//                } else {
//                    // Show the permission request
//                    ActivityCompat.requestPermissions(
//                        this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
//                        REQUEST_PERMISSION_LOCATION
//                    )
//                    false
//                }
//            } else {
//                true
//            }
//        }
//
//        fun startLocationUpdates() {
//
//            // Create the location request to start receiving updates
//
//            mLocationRequest!!.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
//            mLocationRequest!!.setInterval(INTERVAL)
//            mLocationRequest!!.setFastestInterval(FASTEST_INTERVAL)
//
//            // Create LocationSettingsRequest object using location request
//            val builder = LocationSettingsRequest.Builder()
//            builder.addLocationRequest(mLocationRequest!!)
//            val locationSettingsRequest = builder.build()
//
//            val settingsClient = LocationServices.getSettingsClient(this)
//            settingsClient.checkLocationSettings(locationSettingsRequest)
//
//            mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
//            // new Google API SDK v11 uses getFusedLocationProviderClient(this)
//            if (ActivityCompat.checkSelfPermission(
//                    this,
//                    Manifest.permission.ACCESS_FINE_LOCATION
//                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
//                    this,
//                    Manifest.permission.ACCESS_COARSE_LOCATION
//                ) != PackageManager.PERMISSION_GRANTED
//            ) {
//
//
//                return
//            }
//            mFusedLocationProviderClient!!.requestLocationUpdates(
//                mLocationRequest, mLocationCallback,
//                Looper.myLooper()
//            )
//        }
//
//        private val mLocationCallback = object : LocationCallback() {
//            override fun onLocationResult(locationResult: LocationResult) {
//                // do work here
//                locationResult.lastLocation
//                onLocationChanged(locationResult.lastLocation)
//            }
//        }


        override fun bind(post: Post) {
            with(itemView) {
                data_text.text = post.created
                author_text.text = post.author
                post_text.text = post.content
                time_text.text = post.resource


                latitude_text.text = post.location.first.toString()
                longitude_text.text = post.location.second.toString()
                share_text.text = countShare.toString()
                chat_text.text = post.countCommit.toString()
                like_text.text = post.countLike.toString()

                if (countShare != 0) {
                    share_btn.setImageResource(R.drawable.ic_share_24dp)
                    share_text.setTextColor(Color.rgb(255, 0, 0))
                }


                if (countChat != 0) {
                    btn_image_chat.setImageResource(R.drawable.ic_chat_bubble_24dp)
                    chat_text.setTextColor(Color.rgb(255, 0, 0))
                }

                if (post.likedByMe) {

                    btn_image_like.setImageResource(R.drawable.ic_favorite_red_24dp)
                    post.countLike++
                } else {
                    btn_image_like.setImageResource(R.drawable.ic_favorite_inactive_24dp)

                }
            }

        }
    }