package com.namnp.androidjetpack.di

import android.util.Log
import dagger.Lazy
import javax.inject.Inject
import javax.inject.Singleton

@Singleton // now, instead of creating a new instance, Dagger reuses the existing one
// lazy injection constructor
class SmartPhone @Inject constructor(val battery: Lazy<Battery>, val simCard: Lazy<SIMCard>, val memoryCard: Lazy<MemoryCard>) {

    init {
        battery.get().getPower() // lazy injection
        simCard.get().getConnection()
        memoryCard.get().getSpaceAvailablity()
        Log.i("DI", "SmartPhone Constructed")
    }

    fun makeACallWithRecording() {
        Log.i("DI", "Calling.....")
    }
}

