package com.namnp.androidjetpack.coroutines

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.supervisorScope

fun main() {
    try {   // -> have to handle exception (try/catch) in root coroutine (when child coroutines throw exceptions)
        exceptionIsPropagatedUp()
    } catch (e: Exception){
        println("Caught exception 1: $e")
    }
    handlingExceptionInAsync()
    handlingExceptionUsingCoroutineExceptionHandler()
}

private fun exceptionIsPropagatedUp() = runBlocking {
    launch {
        launch {
            try {
                launch {
                    // exception is propagated up the the parent coroutines, if not handled -> crash
                    // -> have to handle exception (try/catch) in root coroutine
                    throw Exception()
                }
            }catch (e: Exception){
                println("Caught exception: $e")
            }
        }
    }
}

private fun handlingExceptionInAsync() = runBlocking {
//    launch {// if not use await(), error still occurs cause in launch {}, error is propagated up
    val deferred = async {// if use async -> no errors, if a function calls await() outside -> crash
        val name: Deferred<String> = async {
            delay(500)
            throw Exception("Error")
            "Namnpse"
        }
        // launch: exceptions are thrown in launch {}
        // async: exceptions are thrown when calling await() -> use try/catch or handling exception here
//        println("name: ${name.await()}") // exception occur when uncomment this line
    }

    launch {
        val resultWithError = deferred.await() // call await() -> crash here
        // -> have to handle error here -> no error
        try {
            val resultWithNoError = deferred.await()
        }catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
// NOTE: handling errors using try/catch is working, but not recommended, not a good way
// -> use CoroutineExceptionHandler

private fun handlingExceptionUsingCoroutineExceptionHandler() = runBlocking {
    val handlerException = CoroutineExceptionHandler { coroutineContext, throwable ->
        // coroutineContext: context information about which coroutine throws exceptions
        println("Caught exception in handler: $throwable")
    }
    launch {
        throw Exception("Error")
    }
}

// SCOPE
// decide how a coroutines or child coroutines is cancelled, how cancellation is handled
// viewModelScope, lifecycleScope: SupervisorJob() + Dispatchers.Main.immediate
// -> coroutines run and fail independently
private fun coroutineScope() = runBlocking {
    val handlerException = CoroutineExceptionHandler { _, throwable ->
        println("Caught exception in Coroutine 1: $throwable")
    }
    // if add handlerException to parent -> only handle crash, but 2 is still cancelled (because we use CoroutineScope())
    // CoroutineScope: if 1 coroutine failed (no matter handling exception or not), it cancels all child coroutines
    // supervisorScope -> children coroutines failed independently, doesn't affect other child coroutines
    // 1 error -> 2 still running
//    CoroutineScope(Dispatchers.Main + handlerException).launch {
    supervisorScope {
//        launch {
//            delay(200)
//            throw Exception("Coroutine 1 is failed")
//        }
//
//        launch {
//            delay(300)
//            println("Coroutine 2 is finished")
//        }
        // 1 failed -> 2 is cancelled
        // add handlerException to parent coroutines: CoroutineScope(Dispatchers.Main + handlerException)
        // if add handlerException -> only handle crash, but 2 is not running

        launch() {
            delay(200)
            throw Exception("Coroutine 1 is failed")
        }

        launch {
            delay(300)
            println("Coroutine 2 is finished")
        }
    }

}

private fun mistakesInLifecycleScope() = runBlocking {
//    lifeCycleScope.launch {
//
//    }

    val job = launch {
        try {
            delay(500)
        }catch (e: Exception) {
            e.printStackTrace()
            if(e is CancellationException) {
                throw e
            }
        }
        // call the second api
        println("Coroutine 1 finished")
    }
    delay(300L)
    job.cancel()
    // cancel job before it finishes -> CancellationException -> consume by try/catch
    // -> error not propagated up -> println("Coroutine 1 finished")
    // -> coroutines run until finish -> waste resources -> MISTAKE
//    -> Solution:
//    1. don't catch Exception(cause it includes CancellationException), catch a specific exception like Http/IO exception
//    -> then, CancellationException is thrown and propagated up -> 1 cannot be finished -> avoid wasting resource and bug
//    2. If still want to catch Exception, then need to check CancellationException in catch block and throw it
//    catch (e: Exception) {
//        e.printStackTrace()
//        if(e is CancellationException) {
//            throw e
//        }
//    }
//    3. use isActive -> GOOD
//    val job = launch {
//        delay(500)
//        if(isActive) {
//            println("Coroutine 1 finished")
//        }
//    }
//    delay(300)
//    job.cancel()
}