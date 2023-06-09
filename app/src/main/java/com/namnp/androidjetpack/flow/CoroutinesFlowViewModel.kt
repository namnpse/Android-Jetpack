package com.namnp.androidjetpack.flow

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

class CoroutinesFlowViewModel: ViewModel() {
    val myFlow = flow<Int> {
        for (i in 0..10) {
            emit(i)
            delay(1000L)
        }
    }
}