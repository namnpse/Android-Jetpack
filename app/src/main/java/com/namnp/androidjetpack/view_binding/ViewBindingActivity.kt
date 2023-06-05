package com.namnp.androidjetpack.view_binding

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.namnp.androidjetpack.R
import com.namnp.androidjetpack.databinding.ActivityViewBindingBinding
import kotlin.math.roundToInt

class ViewBindingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityViewBindingBinding
    private var isStarted = false
    private lateinit var serviceIntent : Intent
    private var time = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewBindingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnStart.setOnClickListener {
            startOrStop()
        }
        binding.btnReset.setOnClickListener {
            reset()
        }

        serviceIntent = Intent(applicationContext,StopWatchService::class.java)
        registerReceiver(updateTime, IntentFilter(StopWatchService.UPDATED_TIME))

    }

    private fun startOrStop() {
        if (isStarted)
            stop()
        else
            start()
    }

    private fun start() {
        serviceIntent.putExtra(StopWatchService.CURRENT_TIME,time)
        startService(serviceIntent)
        binding.btnStart.text = "Stop"
        isStarted = true
    }

    private fun stop() {
        stopService(serviceIntent)
        binding.btnStart.text = "Start"
        isStarted = false
    }

    private fun reset() {
        stop()
        time = 0.0
        binding.tvTime.text = getFormattedTime(time)

    }

    private val updateTime : BroadcastReceiver = object : BroadcastReceiver(){
        override fun onReceive(context: Context, intent: Intent) {
            time = intent.getDoubleExtra(StopWatchService.CURRENT_TIME,0.0)
            binding.tvTime.text = getFormattedTime(time)
        }
    }

    private fun getFormattedTime(time:Double):String{
        val timeInt = time.roundToInt()
        val hours = timeInt % 86400 / 3600
        val minutes = timeInt % 86400 % 3600 / 60
        val seconds = timeInt % 86400 % 3600 % 60

        return String.format("%02d:%02d:%02d",hours,minutes,seconds)
    }
}