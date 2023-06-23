package com.namnp.androidjetpack.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.RemoteInput
import com.namnp.androidjetpack.R

class NotificationActivity : AppCompatActivity() {
    private val channelID = "com.namnp.androidjetpack.channel1"
    private var notificationManager: NotificationManager? = null
    private val KEY_REPLY_IN_NOTI = "key_reply"

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

        val tapResultIntent = Intent(this, NotificationSecondActivity::class.java)
        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            this,
            0,
            tapResultIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        //action button details
        val intentDetails = Intent(this, NotificationDetailsActivity::class.java)
        val pendingIntentDetails: PendingIntent = PendingIntent.getActivity(
            this,
            0,
            intentDetails,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        val actionDetails: NotificationCompat.Action =
            NotificationCompat.Action.Builder(0, "Details", pendingIntentDetails).build()
        // action button settings
        val intentSettings = Intent(this, NotificationSettingsActivity::class.java)
        val pendingIntentSettings: PendingIntent = PendingIntent.getActivity(
            this,
            0,
            intentSettings,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        val actionSettings: NotificationCompat.Action =
            NotificationCompat.Action.Builder(0, "Settings", pendingIntentSettings).build()

        //reply action
        val remoteInput: RemoteInput = RemoteInput.Builder(KEY_REPLY_IN_NOTI).run{
            setLabel("Insert your name here")
            build()
        }

        val replyAction : NotificationCompat.Action = NotificationCompat.Action.Builder(
            0,
            "REPLY",
            pendingIntent
        ).addRemoteInput(remoteInput)
            .build()

        val notificationId = 45
        val notification = NotificationCompat.Builder(this@NotificationActivity, channelID)
            .setContentTitle("Notification Title")
            .setContentText("Notification Content")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .addAction(actionDetails)
            .addAction(actionSettings)
            .addAction(replyAction)
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