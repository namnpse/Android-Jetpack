package com.namnp.androidjetpack.paging.doggo.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.namnp.androidjetpack.R

class DoggoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doggo)
        setUpView()
    }

    private fun setUpView() {
        findViewById<BottomNavigationView>(R.id.navBottomBar).apply {
            setupWithNavController(findNavController(R.id.fragmentNavHost))
        }
    }
}