package com.namnp.androidjetpack

import android.app.Application
import com.namnp.androidjetpack.di.DaggerSmartPhoneComponent
import com.namnp.androidjetpack.di.MemoryCardModule
import com.namnp.androidjetpack.di.SmartPhoneComponent

class AndroidJetpackApplication : Application() {
    lateinit var smartPhoneComponent: SmartPhoneComponent
    override fun onCreate() {
        smartPhoneComponent = initDagger()
        super.onCreate()
    }

    private fun initDagger(): SmartPhoneComponent =
        DaggerSmartPhoneComponent.create()
}