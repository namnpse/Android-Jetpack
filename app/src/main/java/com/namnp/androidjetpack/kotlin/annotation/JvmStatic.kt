package com.namnp.androidjetpack.kotlin.annotation

object AppUtils {

    @JvmStatic // tell the compiler that the method is a static method and can be used in Java code
    fun install() {

    }

}

fun main() {
    // call in Kotlin
    AppUtils.install()

    // call in Java
//    AppUtils.install(); // compilation error
//    -> fix:
//    AppUtils.INSTANCE.install(); // works
}