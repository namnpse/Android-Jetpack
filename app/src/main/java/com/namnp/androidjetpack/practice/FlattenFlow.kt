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
    flatMapLatestOperator()
}

fun flatMapMergeOperator() = runBlocking {
    val startTime = System.currentTimeMillis()
    (1..3).asFlow().onEach { delay(100) }
        .flatMapMerge { getFlow(it) }
        .collect {
            println("Value is $it, time taken is ${System.currentTimeMillis() - startTime} ms")
        }
}

fun flatMapConcatOperator() = runBlocking {
    val startTime = System.currentTimeMillis()
    (1..3).asFlow().onEach { delay(100) }
        .flatMapConcat { getFlow(it) }
        .collect {
            println("Value is $it, time taken is ${System.currentTimeMillis() - startTime} ms")
        }
}

fun flatMapLatestOperator() = runBlocking {
    val startTime = System.currentTimeMillis()
    (1..3).asFlow().onEach { delay(100) }
        .flatMapLatest { getFlow(it) }
        .collect {
            println("Value is $it, time taken is ${System.currentTimeMillis() - startTime} ms")
        }
}

fun getFlow(value: Int) = flow {
    emit("First emitted value $value")
    delay(500)
    emit("Second emitted value $value")
}
