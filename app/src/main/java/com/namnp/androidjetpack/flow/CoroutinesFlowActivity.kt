package com.namnp.androidjetpack.flow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.namnp.androidjetpack.compose.ui.theme.AndroidJetpackTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

class ComposeStateActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val myFlow = flow<Int> {
            for (i in 0..10) {
                emit(i)
                delay(1000L)
            }
        }
        setContent {
            val value = myFlow.collectAsState(initial = 0)
            AndroidJetpackTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Text(text = "Hello: $value")
                }
            }
        }

    }
}