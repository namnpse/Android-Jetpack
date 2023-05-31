package com.namnp.androidjetpack.view_model

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.namnp.androidjetpack.R
import com.namnp.androidjetpack.databinding.ActivityViewModelBinding

class ViewModelActivity : AppCompatActivity() {
    private lateinit var binding: ActivityViewModelBinding
    private lateinit var viewModel: CountViewModel
    private lateinit var viewModelFactory: CustomViewModelFactory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_view_model)
        viewModelFactory = CustomViewModelFactory(startingTotal = 10)
        viewModel = ViewModelProvider(this, viewModelFactory)[CountViewModel::class.java]

        // Ex 1
//        binding.tvCount.text = viewModel.getCount().toString()
//        binding.submitButton.setOnClickListener {
//            binding.tvCount.text = viewModel.getUpdatedCount().toString()
//        }

        // Ex 2
        // create custom view model by using View Model Factory to pass arguments to view model
        // if pass arguments directly without using View Model factory -> error
        binding.tvCount.text = viewModel.getTotal().toString()
        binding.submitButton.setOnClickListener {
            viewModel.add(binding.edtNumber.text.toString().toInt())
            binding.tvCount.text = viewModel.getTotal().toString()
        }


    }
}