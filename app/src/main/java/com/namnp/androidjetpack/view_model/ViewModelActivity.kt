package com.namnp.androidjetpack.view_model

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.namnp.androidjetpack.R
import com.namnp.androidjetpack.databinding.ActivityViewModelBinding

class ViewModelActivity : AppCompatActivity() {
    private lateinit var binding: ActivityViewModelBinding
    private lateinit var viewModel: VMViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_view_model)
        viewModel = ViewModelProvider(this)[VMViewModel::class.java]
        binding.tvCount.text = viewModel.getCount().toString()
        binding.submitButton.setOnClickListener {
            binding.tvCount.text = viewModel.getUpdatedCount().toString()
        }
    }
}