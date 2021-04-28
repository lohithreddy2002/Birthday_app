package com.example.birthday_app

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat

class service:Service() {

    companion object{
        fun startService(context: Context, message: String) {
            val startIntent = Intent(context, service::class.java)
            startIntent.putExtra("inputExtra", message)
            ContextCompat.startForegroundService(context, startIntent)
        }
        fun stopService(context: Context) {
            val stopIntent = Intent(context, service::class.java)
            context.stopService(stopIntent)
        }
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val input = intent?.getStringExtra("inputExtra")
        val notification = NotificationCompat.Builder(this, "1")
                .setContentTitle("notificationTitle")
                .setContentText(input)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setNotificationSilent()
                .build()


        startForeground(1, notification)
// 2
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}