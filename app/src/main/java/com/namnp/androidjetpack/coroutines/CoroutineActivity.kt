package com.namnp.androidjetpack.coroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.namnp.androidjetpack.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CoroutineActivity : AppCompatActivity() {
    private val viewModel: CoroutineViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coroutine)

//        viewModel.getUsers()
        viewModel.users.observe(this) { users ->
            users?.forEach {
                Log.i("CoroutineActivity", "name is ${it.name}")
            }
        }

        lifecycleScope.launch(Dispatchers.IO) {
            Log.i("CoroutineActivity", "Thread is ${Thread.currentThread().name}")
        }

        lifecycleScope.launchWhenCreated {
            // launch when activity/fragment is created
        }

        lifecycleScope.launchWhenStarted {
            // launch when activity/fragment is started
        }

        lifecycleScope.launchWhenResumed {
            // launch when activity/fragment is resumed
        }
    }
}

// NOTE:
//
