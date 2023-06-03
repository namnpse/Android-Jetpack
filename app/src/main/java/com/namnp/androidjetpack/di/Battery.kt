package com.namnp.androidjetpack.di

import android.util.Log
import javax.inject.Inject

class Battery @Inject constructor() {
    init {
        Log.i("DI","Battery Constructed")
    }

    fun getPower(){
        Log.i("DI","Battery available")
    }
}