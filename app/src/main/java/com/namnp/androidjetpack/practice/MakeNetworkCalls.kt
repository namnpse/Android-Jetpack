package com.namnp.androidjetpack.practice

import com.namnp.androidjetpack.coroutines.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.withContext

const val TAG = "MakeNetworkCalls"

fun main() {
//    makeNetworkCallsInSeries()
    makeNetworkCallsParallel()
}

suspend fun getUsers() {
    withContext(Dispatchers.IO) {
        // fetch user
        // save in database
        println("get user 1")
//        delay(2000) // for makeNetworkCallsInSeries
        delay(200) // for makeNetworkCallsParallel
        println("done get user 1")
        throw Exception("EEEE")
    }
}

suspend fun getMoreUsers() {
    withContext(Dispatchers.IO) {
        // fetch user
        // save in database
        println("get get user 2")
        delay(500)
        println("done get user 2")
    }
}

// sequentially
//println("get user 1")
//println("done get user 1")
//println("get get user 2")
//println("done get user 2")
fun makeNetworkCallsInSeries() = runBlocking {
    println("makeNetworkCallsInSeries")
    // CACH 1
//    1 fail -> go to try/catch -> not execute 2
//    launch {
//        try {
//            val user1 = getUsers()
//
//            val user2 = getMoreUsers()
//        } catch (exception: Exception) {
//            println("$exception handled !")
//        }
//    }

    // CACH 2
//    if 1 fail -> return empty list in the first call
//    -> continue 2 -> OK
    launch {
        val user1 = try {
            getUsers()
        } catch (e: Exception) {
            emptyList<User>()
        }
        val user2 = try {
            getMoreUsers()
        } catch (e: Exception) {
            emptyList<User>()
        }
    }
}

//    get user 1
//    get get user 2
//    done get user 2
//    done get user 1
fun makeNetworkCallsParallel() = runBlocking {
    println("makeNetworkCallsParallel")
    //  CACH 1
    // if any error (1->2) -> crash app and not go to try/catch
//    launch {
//        try {
//            val usersDeferred = async {  getUsers() }
//            val moreUsersDeferred = async { getMoreUsers() }
//            val user1 = usersDeferred.await()
//            val user2 = moreUsersDeferred.await()
//        } catch (exception: Exception) {
//            println("$exception handled !")
//        }
//    }

    // CACH 2
    // use coroutine scope, if error -> can handle exception
//    get user 1
//    get get user 2
//    done get user 1
//    -> but handle only crash app, not handle continuing with response from other network calls
//    ( 1 job fail -> all other jobs fail)
//    launch {
//        try {
//            coroutineScope { // coroutineScope will cancel whenever any of its children fail
//                val usersDeferred = async {  getUsers() }
//                val moreUsersDeferred = async { getMoreUsers() }
//                val users = usersDeferred.await()
//                val moreUsers = moreUsersDeferred.await()
//            }
//        } catch (exception: Exception) {
//            println("$exception handled !")
//        }
//    }

    // CACH 3
//    run parallel. If 1 error -> continue job 2
//    get user 1
//    get get user 2
//    done get user 1
//    done get user 2
    launch {
        supervisorScope { // supervisorScope won't cancel other children when one of them fails
            val usersDeferred = async { getUsers() }
            val moreUsersDeferred = async { getMoreUsers() }
            val users = try {
                usersDeferred.await()
            } catch (e: Exception) {
                emptyList<User>()
            }
            val moreUsers = try {
                moreUsersDeferred.await()
            } catch (e: Exception) {
                emptyList<User>()
            }
        }
    }
}

// NOTE
//While NOT using async, we can go ahead with the try-catch or the CoroutineExceptionHandler and achieve anything based on our use cases.
//While using async, try-catch/coroutineScope/supervisorScope.
//With async, use supervisorScope with the individual try-catch for each task, when you want to continue with other tasks if one or some of them have failed.
//With async, use coroutineScope with the top-level try-catch, when you do NOT want to continue with other tasks if any of them have failed.

