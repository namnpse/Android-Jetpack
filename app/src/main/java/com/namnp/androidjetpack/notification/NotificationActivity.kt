package com.namnp.androidjetpack.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import com.namnp.androidjetpack.R

class NotificationActivity : AppCompatActivity() {
    private val channelID = "com.namnp.androidjetpack.channel1"
    private var notificationManager: NotificationManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)
        val button = findViewById<Button>(R.id.button)
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        createNotificationChannel(channelID, "Channel", "Hello Nam")
        button.setOnClickListener {
            displayNotification()
        }
    }


    private fun displayNotification() {
        val notificationId = 45
        val notification = NotificationCompat.Builder(this@NotificationActivity, channelID)
            .setContentTitle("Demo Title")
            .setContentText("This is a demo notification")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()
        notificationManager?.notify(notificationId, notification)

    }

    private fun createNotificationChannel(id: String, name: String, channelDescription: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(id, name, importance).apply {
                description = channelDescription
            }
            notificationManager?.createNotificationChannel(channel)
        }
    }
}

// NOTE
//1. For Android 13+ (API level 33+)
//<uses-permission android:name="android.permission.POST_NOTIFICATIONS"/>