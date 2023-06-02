package com.namnp.androidjetpack

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.namnp.androidjetpack.coroutines.CoroutineActivity
import com.namnp.androidjetpack.data_binding.TwoWayBindingActivity
import com.namnp.androidjetpack.navigation.NavigationActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        val intent = Intent(this, TwoWayBindingActivity::class.java)
//        val intent = Intent(this, NavigationActivity::class.java)
        val intent = Intent(this, CoroutineActivity::class.java)
        startActivity(intent)
    }
}