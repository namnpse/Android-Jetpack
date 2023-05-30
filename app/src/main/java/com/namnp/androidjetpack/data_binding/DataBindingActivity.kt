package com.namnp.androidjetpack.data_binding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.namnp.androidjetpack.R
import com.namnp.androidjetpack.databinding.ActivityDataBindingBinding

// Steps:
// 1. Enable data binding in gradle file
// 2. Wrap xml with <layout> tag
// 3. Create data binding object using DataBindingUtil
// 4. Eliminate findViewById()

class DataBindingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDataBindingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_data_binding)

        binding.submitButton.setOnClickListener {
            displayGreeting()
        }
    }

    private fun displayGreeting() {
        binding.apply {
            greetingTextView.text = "Hello! " + nameEditText.text
        }
    }
}