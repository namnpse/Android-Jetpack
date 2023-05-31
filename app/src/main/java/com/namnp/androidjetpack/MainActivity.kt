package com.namnp.androidjetpack

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.namnp.androidjetpack.data_binding.TwoWayBindingActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val intent = Intent(this, TwoWayBindingActivity::class.java)
        startActivity(intent)
    }
}