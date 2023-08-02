package com.namnp.androidjetpack.rxjava

import android.util.Log
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers

fun main() {
    createAndFromCallableOperators()
}

private fun createAndFromCallableOperators() {
    // can emit multiple values
    val create = Observable.create<String> { emitter ->
        // do something and emit first item
        // can check isDisposed easily
        if (!emitter.isDisposed) {
            emitter.onNext("One")
        }
        // do something and emit second item
        if (!emitter.isDisposed) {
            emitter.onNext("Two")
        }
        // on complete
        if (!emitter.isDisposed) {
            emitter.onComplete()
        }
    }
        .subscribeOn(Schedulers.io())
        .subscribe { item ->
            Log.d("CreateExample", "item : $item")
        }

    // emit ONLY ONE value
    val fromCallable = Observable.fromCallable<String> {
        // do something and return
        return@fromCallable "Namnpse"
    }
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe { item ->
            Log.d("FromCallableExample", "item : $item")
        }
}

// NOTE:
// 1.
// + create and emit multiple value and can check isDisposed easily
// + fromCallable emit ONLY ONE value, cannot check isDisposed directly, emit after disposal -> error, crash
// 2. Both are defer (lazy, run when has observer)