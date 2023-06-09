package com.namnp.androidjetpack.flow

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class CoroutinesFlowViewModel: ViewModel() {

    // StateFlow and SharedFlow
    // use for state, have init value, emit only if have different values
    private val _stateFlow = MutableStateFlow<Int>(0) // must have initial value
    val stateFlow: StateFlow<Int> = _stateFlow

    // use for event, don't have init value, can emit the same values
    private val _message = MutableSharedFlow<String>() // don't have have initial value
    val message: SharedFlow<String> = _message


    val myFlow = flow<Int> {
        for (i in 0..10) {
            emit(i)
            delay(1000L)
        }
    }

    init {
        backPressure()
    }

    private fun backPressure() {
        val myFlow = flow<Int> {
            for (i in 0..10) {
                Log.i("CoroutinesFlowViewModel", "Produce $i")
                emit(i)
                delay(1000L)
            }
        }
            // normal, no backpressure
//        viewModelScope.launch {
//            myFlow.collect {
//                Log.i("CoroutinesFlowViewModel", "Cosumed: $it")
//            }
//        }
        // back pressure
        // use buffer, collectLatest to handle backpressure
        viewModelScope.launch {
            myFlow
                .buffer()
                .collectLatest {
                delay(2000L)
                Log.i("CoroutinesFlowViewModel", "Cosumed: $it")
            }
        }
    }

    fun updateStateFlowAndSharedFlow(value: Int) {
        viewModelScope.launch {
            _stateFlow.emit(value+1)
        }

        viewModelScope.launch {
            _message.emit("Back to Main Activity")
        }
    }
}

// Result:
/*collect {}
Produced 1
Consumed 1
1s
Produced 2
Consumed 2*/
/*collect {}
Produced 1
Consumed 1
3s (1+2) between each emission -> Flow auto handle backpressure
Produced 2
Consumed 2*/
//Flow does not emit the next item until the previously emitted item get consumed.
//Producer makes sure to only give data to the consumer when consumer can consume them.
//-> WAITING FOR CONSUMER TO CONSUME DATA
// If don't want to wait, use buffer() operator
// NOTE:
//1. BACK PRESSURE
//When data is produced faster than it can be consumed.
//That pressure occurs when we consume slower than we produce.
//2. buffer()
// + creates a separate routine for the consumer to run.
// + both producer and consumer will run on 2 different routines.
//Produced 1
//Produced 2
//Consumed 1
//Produced 3
//Produced 4
//Consumed 2*/
//3. collectLatest {}
// + only consume the latest value.
// + use Buffer and collectLatest to handle back pressure in flows.
//Produced 1
//Produced 2
//...
//Produced 10
//Consumed 10
//4. StateFlow and SharedFlow
// StateFlow: use for state, have init value, emit only if have different values
// SharedFlow: use for event, don't have init value, can emit the same values