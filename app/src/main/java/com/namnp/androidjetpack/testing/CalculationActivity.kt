package com.namnp.androidjetpack.testing

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.namnp.androidjetpack.R
import com.namnp.androidjetpack.databinding.ActivityCalculationBinding

class CalculationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCalculationBinding
    private lateinit var viewModel: CalcViewModel
    lateinit var factory: CalcViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_calculation)
        factory = CalcViewModelFactory(CalculationImpl())
        viewModel = ViewModelProvider(this, factory)
            .get(CalcViewModel::class.java)
        binding.myViewModel = viewModel
        binding.lifecycleOwner = this
    }
}