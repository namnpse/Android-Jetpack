package com.namnp.androidjetpack.di

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.namnp.androidjetpack.AndroidJetpackApplication
import com.namnp.androidjetpack.R
import dagger.Lazy
import javax.inject.Inject

class LazyInjectionActivity : AppCompatActivity() {
    // inject field
    @Inject
    lateinit var smartPhone: Lazy<SmartPhone> // lazy inject, only init object when smartPhone.get() is called
    // cannot use private, if not -> cannot be injected

    // can get any other DI object by using @Inject field like above
//    @Inject
//    lateinit var memoryCard: MemoryCard

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
//        DaggerSmartPhoneComponent.create()
//            .getSmartPhone()
//            .makeACallWithRecording()

        // with Inject Field
//        DaggerSmartPhoneComponent.create().inject(this)
//        smartPhone.makeACallWithRecording()

//        we created the component in our main activity.
//        If there were 10 activities we would have to write this same code 10 times.
//        -> The recommended best practice here is writing this code part in a subclass of the application class. (1 time)
        (application as AndroidJetpackApplication).smartPhoneComponent
            .inject(this) // inject new instance every time call inject() func
        val battery = smartPhone.get().battery // only init object when smartPhone.get() is called
        println(battery.getPower())
    // ( try rotate screen or call inject in other activities to see)
    // -> to avoid this: use @Singleton annotation
    }
}

// NOTE:
// 1.if use module (and provide MemoryCard() instance in module) then don't need to use MemoryCard @Inject constructor
// 2. Binds use for ABSTRACT fun that already provide DI via @Inject constructor -> make module abstract, check NCBatteryModule for more
// 3. we created the component in our main activity.
// If there were 10 activities we would have to write this same code 10 times.
// -> The recommended best practice here is writing this code part in a subclass of the application class. (1 time,  run before any activities)
// 4. use @Singleton -> instead of creating a new instance, Dagger reuses the existing one
// Ex: WITHOUT @Singleton: (call all constructors again, many times)
//Service Provider Constructed
//SIM Card Constructed
//Memory Card Constructed
//Power from NickelCadmiumBattery
//Service provider connected
//Memory space available
//SmartPhone Constructed
//Calling.....

// is instanced again, many times

//Service Provider Constructed
//SIM Card Constructed
//Memory Card Constructed
//Power from NickelCadmiumBattery
//Service provider connected
//Memory space available
//SmartPhone Constructed
//Calling.....

// WITH @Singleton: (reuse, one times)
//Service Provider Constructed
//SIM Card Constructed
//Memory Card Constructed
//Power from NickelCadmiumBattery
//Service provider connected
//Memory space available
//SmartPhone Constructed
//Calling.....
//Calling.... (reuse)