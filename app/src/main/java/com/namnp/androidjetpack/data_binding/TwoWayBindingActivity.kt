package com.namnp.androidjetpack.data_binding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.namnp.androidjetpack.R
import com.namnp.androidjetpack.databinding.ActivityTwoWayBindingBinding

class TwoWayBindingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTwoWayBindingBinding
    private lateinit var viewModel: TwoWayBindingViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_two_way_binding)
        viewModel = ViewModelProvider(this)[TwoWayBindingViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
//        setContentView(R.layout.activity_two_way_binding)
    }
}