package com.namnp.androidjetpack.coroutines

import kotlinx.coroutines.*

@OptIn(DelicateCoroutinesApi::class)
fun main() {
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