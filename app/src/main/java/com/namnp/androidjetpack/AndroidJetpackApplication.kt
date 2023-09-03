package com.namnp.androidjetpack

import android.app.Application
import com.namnp.androidjetpack.di.DaggerSmartPhoneComponent
import com.namnp.androidjetpack.di.SmartPhoneComponent
import com.namnp.androidjetpack.paging.doggo.data.repository.local.DoggoDatabase
import com.namnp.androidjetpack.paging.doggo.data.repository.local.LocalInjector
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AndroidJetpackApplication : Application() {
    lateinit var smartPhoneComponent: SmartPhoneComponent
    override fun onCreate() {
        smartPhoneComponent = initDagger()
        super.onCreate()
        initForDoggoFeature()
    }

    private fun initDagger(): SmartPhoneComponent =
        DaggerSmartPhoneComponent.create()

    // use for `doggo` feature
    private fun initForDoggoFeature() {
        LocalInjector.appDatabase = DoggoDatabase.getInstance(applicationContext)
    }
}