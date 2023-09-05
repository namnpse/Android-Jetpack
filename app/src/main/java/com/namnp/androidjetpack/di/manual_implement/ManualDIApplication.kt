package com.namnp.androidjetpack.di.manual_implement

import android.app.Application

class ManualDIApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        // need to call the initializeDI function with an application instance.
        // This is the entry point to the DI framework (just like initializing dagger components)
        LibraryModule.initializeDI(this)
    }
}