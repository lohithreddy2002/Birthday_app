package com.example.birthday_app


import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.ContentProviderClient
import android.content.Context
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import java.util.*
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {
    private var finger:Boolean = false
    private lateinit var biometricPrompt:BiometricPrompt
    private lateinit var executor: Executor
    private lateinit var promptInfo: BiometricPrompt.PromptInfo
    private lateinit var auth: FirebaseAuth
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback
    private var curr : Location? = null

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        createchannel()
        setContentView(R.layout.activity_main)
        requestper()
        val executor = ContextCompat.getMainExecutor(this)
        val biometricPrompt =
            BiometricPrompt(this, executor, object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    finger = true
                }
            })
        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("verify it is you").setSubtitle("placefinger print for authentication")
            .setNegativeButtonText("use password")
            .build()


        if (!finger) {
            biometricPrompt.authenticate(promptInfo)

        }
    }


    @SuppressLint("MissingPermission")
    override fun onStart() {
        super.onStart()


    }









    fun hasfiine() = ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
    fun hascoarse() = ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
    @RequiresApi(Build.VERSION_CODES.Q)
    fun hasback() = ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED
    @RequiresApi(Build.VERSION_CODES.Q)
    fun requestper() {
        var list = mutableListOf<String>()
        if (!hasfiine()) {
            list.add(Manifest.permission.ACCESS_FINE_LOCATION)
        }
        if (!hascoarse()) {
            list.add(Manifest.permission.ACCESS_COARSE_LOCATION)
        }
        if (!hasback()) {
            list.add(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
        }

        if (list.isNotEmpty()) {
            requestPermissions(list.toTypedArray(), 1)
        }
    }

    private fun createchannel(){
        val name = "birth_day"
        var description_ = "location data"

        val channel = NotificationChannel("1",name,NotificationManager.IMPORTANCE_DEFAULT).apply {
            description = description_
        }
        val notificationManager:NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.menu,menu)
//        return super.onCreateOptionsMenu(menu)
//    }

    }

