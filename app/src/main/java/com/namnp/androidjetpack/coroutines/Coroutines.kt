package com.namnp.androidjetpack.coroutines

import kotlinx.coroutines.*

@OptIn(DelicateCoroutinesApi::class)
fun main() {
//    coroutineBuilder()
    coroutineScopeFun()
}

fun coroutineBuilder() {
    // launch() -> returns a Job
    // can use Job to cancel coroutines
    GlobalScope.launch {  // launch new coroutine in background and continue
        println("1")
        delay(1000L) // non-blocking delay for 1 second (default time unit is ms)
        println("World!") // print after delay
        val sum1 = async { // non blocking sum1
            delay(100L)
            println("2+2")
            2 + 2
        }
        println("2")
        val sum2 = async { // non blocking sum2
            delay(500L)
            println("3+3")
            3 + 3
        }
        println("3")
        println("waiting concurrent sums")
        val total = sum1.await() + sum2.await() // execution stops until both sums are calculated
        println("Total is: $total")
    }
    println("Hello,")     // main thread continues while coroutine executes
    Thread.sleep(2000L)   // block main thread for 2 seconds to keep JVM alive
}
/*
1
Hello,
World!
2
3
waiting concurrent sums
2+2
3+3
Total is: 10
*/

fun coroutineScopeFun() = runBlocking { // this: CoroutineScope
    launch {
        delay(200L)
        println("Task from runBlocking")
    }

    coroutineScope { // Creates a new coroutine scope
        val job = launch {
            println("Task from nested launch, this is printed")
            delay(500L)
            println("Task from nested launch, this won't be printed")
        }

        delay(100L)
        println("Task from first coroutine scope") // Printed before initial launch
        job.cancel() // This cancels nested launch's execution
    }

    println("Coroutine scope is over") // This is not printed until nested launch completes/is cancelled
}
/*
Task from nested launch, this is printed
Task from first coroutine scope
Coroutine scope is over
Task from runBlocking
*/

fun cancellingJob() = runBlocking {
//    job = launch {
//
//    }
    // job.cancel -> cancel all its children
    // child Job fail or cancel -> its parent cancelled -> exception
    // -> use supervisorJob: Job doesn’t cancel if one of its children fail

    val startTime = System.currentTimeMillis()
    val job = launch(Dispatchers.Default) {
        var nextPrintTime = startTime
        var i = 0
        while (isActive) { // cancelable computation loop
            // print a message twice a second
            if (System.currentTimeMillis() >= nextPrintTime) {
                println("I'm sleeping ${i++} ...")
                nextPrintTime += 500L
            }
        }
    }
    delay(1300L) // delay a bit
    println("main: I'm tired of waiting!")
    job.cancelAndJoin() // cancels the job and waits for its completion
    println("main: Now I can quit.")
}
/*
I'm sleeping 0 ...
I'm sleeping 1 ...
I'm sleeping 2 ...
main: I'm tired of waiting!
main: Now I can quit.*/


@OptIn(DelicateCoroutinesApi::class)
fun coroutineDispatcher() = runBlocking<Unit> {
    launch { //context of the parent, main runBlocking coroutine
        println("main runBlocking: I'm working in thread ${Thread.currentThread().name}")
    }
    launch(Dispatchers.Unconfined) { //not confined -- will inmediatly run in main thread but not after suspension
        println("Unconfined: I'm working in thread ${Thread.currentThread().name}")
        delay(100L) // delays (suspends) execution 100 ms
        println("Unconfined: I'm working in thread ${Thread.currentThread().name}")
    }
    launch(Dispatchers.Default) { //will get dispatched to DefaultDispatcher
        println("Default: I'm working in thread ${Thread.currentThread().name}")
    }
    launch(newSingleThreadContext("MyOwnThread")) {// will get its own new thread
        println("newSingleThreadContext: I'm working in thread ${Thread.currentThread().name}")
    }
}
/*
Unconfined: I'm working in thread main @coroutine#3
Default: I'm working in thread DefaultDispatcher-worker-1 @coroutine#4
main runBlocking: I'm working in thread main @coroutine#2
newSingleThreadContext: I'm working in thread MyOwnThread @coroutine#5
Unconfined: I'm working in thread kotlinx.coroutines.DefaultExecutor @coroutine#3
*/

@OptIn(DelicateCoroutinesApi::class)
fun handlingExceptions() = runBlocking<Unit> {
    // propagating exception to the default Thread.UncaughtExceptionHandler
    // launch -> throw exception immediately
    val job = GlobalScope.launch {
        throw AssertionError()
    }

    // blocks thread execution until coroutine completes
    job.join()

    // launches async coroutine but exception is not propagated until await is called
    val deferred = GlobalScope.async(Dispatchers.Default) {
        println("AAAA") // print but not throw exception until await() is called
        throw AssertionError()
    }

    //defines a specific handler
    val handler = CoroutineExceptionHandler { _, exception ->
        println("We caught $exception")
    }

    // propagating exception using a custom CoroutineExceptionHandler
    GlobalScope.launch(handler) {
        throw AssertionError()
    }

    // This exception is finally propagated calling await and should be handled by user, eg. with try {} catch {}
    deferred.await()
}

/*-> 3 ways to handle exception:
        + try/catch launch{}
        + try catch await()
        + use CoroutineExceptionHandler*/
