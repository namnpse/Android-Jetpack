package com.namnp.androidjetpack.flow

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.namnp.androidjetpack.compose.ui.theme.AndroidJetpackTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ComposeStateActivity : ComponentActivity() {
    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val viewModel = viewModel<CoroutinesFlowViewModel>()
            lifecycleScope.launchWhenStarted {
                viewModel.stateFlow.collect {

                }
            }
            lifecycleScope.launchWhenStarted {
                viewModel.message.collect {

                }
            }
//            --> launchWhenStarted deprecated
//            --> use repeatOnLifecycle(Lifecycle.State.STARTED)

//            -> more optimal, safer way
            collectLatestLifecycleFlow(viewModel.stateFlow) {

            }
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.stateFlow.collect {

                    }
                }
            }
            val value = viewModel.myFlow.collectAsState(initial = 0)
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


fun <T> ComponentActivity.collectLatestLifecycleFlow(flow: Flow<T>, collect: suspend (T) -> Unit) {
    lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            flow.collectLatest(collect)
        }
    }
}

