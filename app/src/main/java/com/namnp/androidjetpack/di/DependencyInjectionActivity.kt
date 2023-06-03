package com.namnp.androidjetpack.di

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.namnp.androidjetpack.R

class DependencyInjectionActivity : AppCompatActivity() {

//    private lateinit var smartPhone: SmartPhone

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dependency_injection)

        // without DI
//        val smartPhone = SmartPhone(
//            Battery(),
//            SIMCard(ServiceProvider()),
//            MemoryCard(),
//        )
//        smartPhone.makeACallWithRecording()

        // with DI
        DaggerSmartPhoneComponent.create()
            .getSmartPhone()
            .makeACallWithRecording()
    }
}

// NOTE:
// if use module (and provide MemoryCard() instance in module) then don't need to use MemoryCard @Inject constructor
// Binds use for ABSTRACT fun that already provide DI via @Inject constructor -> make module abstract, check NCBatteryModule for more