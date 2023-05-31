package com.namnp.androidjetpack.view_model

import androidx.lifecycle.ViewModel

class CountViewModel(startingTotal: Int): ViewModel() {
    private var count = 0
    private var total = 0

    init {
        total = startingTotal
    }

    fun getCount(): Int = count

    fun getUpdatedCount(): Int = ++count

    fun getTotal() = total

    fun add(value: Int) {
        total+=value
    }
}