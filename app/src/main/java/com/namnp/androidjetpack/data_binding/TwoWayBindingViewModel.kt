package com.namnp.androidjetpack.data_binding

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TwoWayBindingViewModel: ViewModel() {
    private var _name: MutableLiveData<String> = MutableLiveData("") // can update
    val name: LiveData<String> = _name // readable, cannot update, if call setValue() -> error
//    val totalLiveData: LiveData<Int>
//        get() = _totalLiveData

    val userName = MutableLiveData<String>()
    init {
        userName.value = "Frank"
    }
}