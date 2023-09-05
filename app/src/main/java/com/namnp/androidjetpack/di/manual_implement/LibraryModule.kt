package com.namnp.androidjetpack.di.manual_implement

import android.app.Application

object LibraryModule {

    // lateinit var and the @Volatile annotation to make it thread-safe
    @Volatile
    lateinit var application: Application

    fun initializeDI(_application: Application) {
        if(!::application.isInitialized) {
            synchronized(this) {
                application = _application
            }
        }
    }
}