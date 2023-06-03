package com.namnp.androidjetpack.di

import android.util.Log
import javax.inject.Inject

class MemoryCard
//    @Inject constructor() // removed @Inject constructor cause it's already provided in Module
{
    init {
        Log.i("DI","Memory Card Constructed")
    }

    fun getSpaceAvailablity(){
        Log.i("DI","Memory space available")
    }
}