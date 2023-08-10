package com.namnp.androidjetpack.practice

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking<Unit> {

    launch {
        Thread.sleep(3000L)
        // delay(3000L)
        println("Coroutine 1 ${Thread.currentThread().name}")
    }

    launch {
        println("Coroutine 2 ${Thread.currentThread().name}")
    }
}

// use Thread.sleep:
//Coroutine 1 main
//Coroutine 2 main
/*
-> when first coroutine is executed, it will come at Thread.sleep(3000L) which in turns block the main thread.
So, main thread is blocked and it will not handle other threads for 3 seconds.
Then after 3 seconds Coroutine 1 main will get printed.
and then Coroutine 2 main will be printed
*/

// use delay
//Coroutine 2 main
//Coroutine 1 main
/*
-> when first coroutine is executed it will come at delay(3000L) which suspends the main thread.
now main Thread will not wait for 3 seconds, instead, it will start executing other coroutines.
Hence, it will execute coroutine 2 and prints Coroutine 2 main first.
Then, after the delay of 3 second is over by coroutine 1, Then main Thread go back to Coroutine 1 and prints Coroutine 1 main
*/

// NOTE
//-> prove that Thread.sleep will blocking the thread,
//whereas delay is just suspending the thread