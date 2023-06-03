package com.namnp.androidjetpack.di

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [MemoryCardModule::class, NCBatteryModule::class])
interface SmartPhoneComponent {

//    fun getSmartPhone(): SmartPhone // dont need any more cause we have @Inject field in activity/fragment by inject() fun below

    // In DependencyInjectionActivity, we can get all DI object in DI graph
    fun inject(dependencyInjectionActivity: DependencyInjectionActivity)
}