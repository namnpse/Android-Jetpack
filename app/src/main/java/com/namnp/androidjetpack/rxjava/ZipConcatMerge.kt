package com.namnp.androidjetpack.rxjava

import com.namnp.androidjetpack.coroutines.model.User
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.BiFunction
import io.reactivex.rxjava3.schedulers.Schedulers

val networkService = NetworkService()
// ZIP
// Zip: used to make parallel network calls
// combines the emissions of multiple Observables via a specified function and emit single items for each combination
fun main() {
    zipOperator()
    concatOperator()
    mergeOperator()
}

// making two network calls in parallel
private fun zipOperator() {
    Observable.zip(
        getCricketFansObservable(),
        getFootballFansObservable(),
        BiFunction<List<User>, List<User>, List<User>> { cricketFans, footballFans ->
            // here we get both the results at a time.
            return@BiFunction filterUserWhoLovesBoth(cricketFans, footballFans)
        })
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
//        .subscribe(getObserver())
        .subscribe(object : Observer<List<User>> {
            override fun onSubscribe(d: Disposable) {
                println("onSubscribe")
            }
            override fun onNext(userList: List<User>) {
                println("onNext : $userList")
            }
            override fun onError(e: Throwable) {
                println("onError : ${e.message}")
            }
            override fun onComplete() {
                println("onComplete")
            }
        })
}
//
//private fun getObserver(): Observer<List<User>> {
//    return object : Observer<List<User>> {
//        override fun onSubscribe(d: Disposable) {
//            println("onSubscribe")
//        }
//
//        override fun onNext(userList: List<User>) {
//            println("onNext : $userList")
//        }
//
//        override fun onError(e: Throwable) {
//            println("onError : ${e.message}")
//        }
//
//        override fun onComplete() {
//            println("onComplete")
//        }
//    }
//}

class NetworkService {

    fun getCricketFansObservable(): Observable<List<User>> {
        return Observable.create<List<User>> { emitter ->
            if (!emitter.isDisposed) {
                // fetch data from network
                val data = fetchUserListFromNetwork()
                emitter.onNext(data)
                emitter.onComplete()
            }
        }.subscribeOn(Schedulers.io())
    }

    fun getFootballFansObservable(): Observable<List<User>> {
        return Observable.create<List<User>> { emitter ->
            if (!emitter.isDisposed) {
                // fetch data from network
                val data = fetchUserListFromNetwork()
                emitter.onNext(data)
                emitter.onComplete()
            }
        }.subscribeOn(Schedulers.io())
    }

    private fun fetchUserListFromNetwork(): List<User> {
        return listOf()
    }
}

fun getCricketFansObservable(): Observable<List<User>> {
    return networkService.getCricketFansObservable()
}

fun getFootballFansObservable(): Observable<List<User>> {
    return networkService.getFootballFansObservable()
}

// function to filter the user who loves both
private fun filterUserWhoLovesBoth(
    cricketFans: List<User>,
    footballFans: List<User>
): List<User> {
    val userWhoLovesBoth = ArrayList<User>()
    for (footballFan in footballFans) {
        if (cricketFans.contains(footballFan)) {
            userWhoLovesBoth.add(footballFan)
        }
    }
    return userWhoLovesBoth
}
// 1A, 2B, 4C, 5D

// CONCAT
private fun concatOperator() {
    val observableA = Observable.fromArray("A1", "A2", "A3", "A4")
    val observableB = Observable.fromArray("B1", "B2", "B3", "B4")
    Observable.concat(observableA, observableB)
        .subscribe(object : Observer<String> {

            override fun onSubscribe(d: Disposable) {

            }

            override fun onNext(value: String) {
                println(value)
            }

            override fun onError(e: Throwable) {

            }

            override fun onComplete() {

            }
        })
}
// NOTE: Concat: maintains the order of the observables while emitting the items.
// it will emit all the items of the first observable(A1,2,3,4) and then it will emit all the items of the second observable(B1,2,3,4) and so on
// OUTPUT:
/*
A1
A2
A3
A4
B1
B2
B3
B4
*/

// MERGE:
// combines multiple Observables into one by merging their emissions.
// It will not maintain the order while emitting the items.
private fun mergeOperator() {
    val observableA = Observable.fromArray("A1", "A2", "A3", "A4")
    val observableB = Observable.fromArray("B1", "B2", "B3", "B4")
    Observable.merge(observableA, observableB)
        .subscribe(object : Observer<String> {

            override fun onSubscribe(d: Disposable) {

            }

            override fun onNext(value: String) {
                println(value)
            }

            override fun onError(e: Throwable) {

            }

            override fun onComplete() {

            }
        })
}

//OUTPUT: ( can be different output, cause it doesn't maintain the order, first come first emit)
/*
A1
A2
B1
A3
B2
B3
A4
B4
*/
