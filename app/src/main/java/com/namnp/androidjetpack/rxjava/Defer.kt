package com.namnp.androidjetpack.rxjava

import android.util.Log
import io.reactivex.rxjava3.core.Observable

fun main() {
    deferOperator()
}

class Car {

    var brand: String = "DEFAULT"

    fun getBrandObservable(): Observable<String> {
        return Observable.just(brand)
    }

    fun getBrandDeferObservable(): Observable<String> {
        return Observable.defer { // Defer: do not create the Observable until the observer subscribes, and create a fresh Observable for each observer
            return@defer Observable.just(brand)
        }
    }

}

// Defer: do not create the Observable until the observer subscribes, and create a fresh Observable for each observer
private fun deferOperator() {
    val car = Car()

    val brandObservable = car.getBrandObservable()
    val brandDeferObservable = car.getBrandDeferObservable()

    car.brand = "BMW"
    // set brand first
    // then subscribe later

    val brandSubscription = brandObservable
        .subscribe { brand ->
            Log.d("DeferExample", "brandObservable : $brand")
        }

    val brandDeferSubscription = brandDeferObservable
        .subscribe { brand ->
            Log.d("DeferExample", "brandDeferObservable : $brand")
        }

// OUTPUT:
//    DeferExample: brandObservable : DEFAULT
//    DeferExample: brandDeferObservable : BMW
}

// NOTE:
// Defer does not create the Observable until the observer subscribes.
// Defer creates a fresh observable for each observer.
