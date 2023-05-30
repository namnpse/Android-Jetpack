package com.namnp.androidjetpack.view_model

import androidx.lifecycle.ViewModel

class VMViewModel: ViewModel() {
    private var count = 0

    fun getCount(): Int = count

    fun getUpdatedCount(): Int = ++count
}