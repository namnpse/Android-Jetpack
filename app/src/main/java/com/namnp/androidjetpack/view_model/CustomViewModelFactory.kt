package com.namnp.androidjetpack.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class CustomViewModelFactory(private val startingTotal: Int) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(CountViewModel::class.java)) {
            return CountViewModel(startingTotal = startingTotal) as T
        }
        throw IllegalArgumentException("Unknown View Model class")
    }
}