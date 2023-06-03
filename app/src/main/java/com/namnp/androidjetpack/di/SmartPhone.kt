package com.namnp.androidjetpack.di

import android.util.Log
import javax.inject.Inject

class SmartPhone @Inject constructor(val battery: Battery, val simCard: SIMCard, val memoryCard: MemoryCard) {

    init {
        battery.getPower()
        simCard.getConnection()
        memoryCard.getSpaceAvailablity()
        Log.i("DI", "SmartPhone Constructed")
    }

    fun makeACallWithRecording() {
        Log.i("DI", "Calling.....")
    }
}

