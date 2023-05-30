package com.namnp.androidjetpack.view_model

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.namnp.androidjetpack.R
import com.namnp.androidjetpack.databinding.ActivityViewModelBinding

class ViewModelActivity : AppCompatActivity() {
    private lateinit var binding: ActivityViewModelBinding
    private var count = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_view_model)

        binding.submitButton.setOnClickListener {
            count++
            binding.tvCount.text = count.toString()
        }
    }
}