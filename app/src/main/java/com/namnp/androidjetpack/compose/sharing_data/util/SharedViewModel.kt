package com.namnp.androidjetpack.compose.sharing_data.util

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SharedViewModel(
    private val savedStateHandle: SavedStateHandle // survive process death -> can restore data/state
): ViewModel() {

    private val _sharedState = MutableStateFlow(0)
    val sharedState = _sharedState.asStateFlow()

    fun updateState() {
        _sharedState.value++
    }

    override fun onCleared() {
        super.onCleared()
        println("ViewModel cleared")
    }
}