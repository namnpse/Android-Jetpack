package com.namnp.androidjetpack.practice

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking

fun main() {
    flatMapMergeOperator()
}

fun flatMapMergeOperator() = runBlocking {
    val startTime = System.currentTimeMillis()
    (1..3).asFlow().onEach { delay(100) }
        .flatMapMerge { getFlow(it) }
        .collect {
            println("Value is $it, time taken is ${System.currentTimeMillis() - startTime} ms")
        }
}
/*
Value is First emitted value 1, time taken is 172 ms
Value is First emitted value 2, time taken is 268 ms
Value is First emitted value 3, time taken is 369 ms
Value is Second emitted value 1, time taken is 673 ms
Value is Second emitted value 2, time taken is 768 ms
Value is Second emitted value 3, time taken is 870 ms
*/

fun flatMapConcatOperator() = runBlocking {
    val startTime = System.currentTimeMillis()
    (1..3).asFlow().onEach { delay(100) }
        .flatMapConcat { getFlow(it) }
        .collect {
            println("Value is $it, time taken is ${System.currentTimeMillis() - startTime} ms")
        }
}
/*
Value is First emitted value 1, time taken is 128 ms
Value is Second emitted value 1, time taken is 629 ms
Value is First emitted value 2, time taken is 730 ms
Value is Second emitted value 2, time taken is 1230 ms
Value is First emitted value 3, time taken is 1330 ms
Value is Second emitted value 3, time taken is 1831 ms
*/

fun flatMapLatestOperator() = runBlocking {
    val startTime = System.currentTimeMillis()
    (1..3).asFlow().onEach { delay(100) }
        .flatMapLatest { getFlow(it) }
        .collect {
            println("Value is $it, time taken is ${System.currentTimeMillis() - startTime} ms")
        }
}
/*
Value is First emitted value 1, time taken is 157 ms
Value is First emitted value 2, time taken is 280 ms
Value is First emitted value 3, time taken is 381 ms
Value is Second emitted value 3, time taken is 882 ms
*/

fun getFlow(value: Int) = flow {
    emit("First emitted value $value")
    delay(500)
    emit("Second emitted value $value")
}
