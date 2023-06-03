package com.namnp.androidjetpack.di

import android.util.Log
import com.namnp.androidjetpack.di.ServiceProvider
import javax.inject.Inject

class SIMCard @Inject constructor(private  val serviceProvider: ServiceProvider) {


    init {
        Log.i("DI","SIM Card Constructed")
    }

    fun getConnection(){
        serviceProvider.getServiceProvider()
    }
}
