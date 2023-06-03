package com.namnp.androidjetpack.di

import android.util.Log
import javax.inject.Inject

class NickelCadmiumBattery @Inject constructor(): Battery {
    override fun getPower() {
        Log.i("MYTAG"," Power from NickelCadmiumBattery")
    }
}