package com.namnp.androidjetpack.compose.state

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class ComposeStateViewModel : ViewModel() {
 var count by mutableStateOf(0)

 fun increaseCount(){
     count++
 }
}