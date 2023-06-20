package com.namnp.androidjetpack.notification

import android.app.NotificationManager
import android.app.RemoteInput
import android.content.Context
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import com.namnp.androidjetpack.R

class NotificationSecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second_notification)
        receiveInput()
    }

    private fun receiveInput() {
        val KEY_REPLY_IN_NOTI = "key_reply"
        val result_text_view = findViewById<TextView>(R.id.result_text_view)
        val intent = this.intent
        val remoteInput = RemoteInput.getResultsFromIntent(intent)
        if (remoteInput != null) {
            val inputString = remoteInput.getCharSequence(KEY_REPLY_IN_NOTI).toString()
            result_text_view.text = inputString

            val channelID = "com.namnp.androidjetpack.channel1"
            val notificationId = 45

            val repliedNotification = NotificationCompat.Builder(this,channelID)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentText("Your reply received")
                .build()
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(notificationId,repliedNotification)
        }
    }
}