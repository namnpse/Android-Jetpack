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

        // Ex 1
        binding.submitButton.setOnClickListener {
            displayGreeting()
        }

        // Ex 2
        binding.student = getStudent()

        // dont need to use this anymore
//        val student = getStudent()
//        binding.userName.text = student.name
//        binding.userEmail.text = student.email
    }

    private fun displayGreeting() {
        binding.apply {
            greetingTextView.text = "Hello! " + nameEditText.text
        }
    }

    private fun getStudent(): Student {
        return Student(id = "1", name = "Nam", email = "nam@gmail.com")
    }
}