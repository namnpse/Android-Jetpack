package com.namnp.androidjetpack.kotlin.annotation

import java.util.Date

//@JvmOverloads: To use the default values passed as an argument in Kotlin code from the Java code
data class Session @JvmOverloads constructor(val name: String, val date: Date = Date())

fun main() {
    // call in Kotlin
    val sessionOne = Session("Session One", Date())
    val sessionTwo = Session("Session Two")

    // call in Java
//    Session sessionOne = new Session("Session One", new Date());
//    Session sessionTwo = new Session("Session Two"); // compilation error -> uses @JvmOverloads
}