package com.namnp.androidjetpack.compose.sharing_data

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

class SharingDataActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NavigationArgs()
        }
    }
}