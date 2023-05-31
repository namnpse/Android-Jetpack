package com.namnp.androidjetpack.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CountViewModel(startingTotal: Int): ViewModel() {
    private var count = 0
    private var total = 0
    private var _totalLiveData: MutableLiveData<Int> = MutableLiveData() // can update
    val totalLiveData: LiveData<Int> = _totalLiveData // readable, cannot update, if call setValue() -> error
//    val totalLiveData: LiveData<Int>
//        get() = _totalLiveData

    init {
        total = startingTotal
    }

    fun getCount(): Int = count

    fun getUpdatedCount(): Int = ++count

    fun getTotal() = total

    fun add(value: Int) {
        total+=value
        _totalLiveData.value = (_totalLiveData.value ?: 0).plus(value)
    }
}