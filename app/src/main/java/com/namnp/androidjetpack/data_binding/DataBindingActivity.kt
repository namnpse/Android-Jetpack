package com.namnp.androidjetpack.data_binding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.namnp.androidjetpack.R
//import com.namnp.androidjetpack.databinding.ActivityDataBindingBinding

class DataBindingActivity : AppCompatActivity() {
//    private lateinit var binding: ActivityDataBindingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_binding)
    }
}