package com.namnp.androidjetpack.di

import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class NCBatteryModule {

//    @Provides
//    fun providesNCBattery(nickelCadmiumBattery: NickelCadmiumBattery):Battery {
//        return NickelCadmiumBattery() // don't need to create instance of NickelCadmiumBattery cause we have NickelCadmiumBattery @Inject constructor
//        return nickelCadmiumBattery
//    }

//    we have already annotated @Inject constructor of the NickelCadmiumBattery class
//    -> don’t need a body for this provides function.
//    -> just define this module as an abstract module. then make this function as abstract. And remove the function body.

    @Binds
    abstract fun providesNCBattery(nickelCadmiumBattery: NickelCadmiumBattery):Battery
}