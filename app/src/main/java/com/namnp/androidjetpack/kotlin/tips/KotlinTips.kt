package com.namnp.androidjetpack.kotlin.tips

fun main() {
    // Swap 2 variables without the third
    var a = 5
    var b = 1
    a = b.also { b = a }

    println("a: $a b: $b")

//    Generate Kotlin Lists With a Single Line of Code
    IntArray(10) { 1 }.asList()

    // normal way
    val list = arrayListOf<Int>()
    for (x in 0 until 10 step 2) {
        list.add(x)
    }
    // Kotlin way (single line)
    val newList = List(5) { it * 2 }
}

fun checkAndRequire() {
    val n = 3
    val arg = true
    if (n < 0) {
        throw IllegalArgumentException("message")
    }
    // use this instead
    // validates the argument passed and throws an IllegalArgumentException if it’s false
    require(n >= 0) { "Number must no be negative" }

    // check function throws IllegalStateException
    // throws IllegalStateException when the object state is false
    check(arg) {
        "message"
    }
    checkNotNull(arg) {
        "message"
    }
}