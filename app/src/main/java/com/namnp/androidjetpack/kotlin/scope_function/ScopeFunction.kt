package com.namnp.androidjetpack.kotlin.scope_function

import android.content.Intent

fun main() {
    letFunction() // it, return last line, use much
    alsoFunction() // it, return object, less used (prefer use apply cause apply don't need to use 'it`)
    applyFunction() // this, return object, use much
    runFunction() // this, return last line (apply = run return this), use less
    withFunction() // this, return last line, rarely used
}

private var number: Int? = null

fun letFunction() {
    // BAD
    if (number != null) {
        // cannot assign, other thread can access `number` and assign value back to null (when inside if block)
        var num2 = number!! + 1
    }

    // GOOD
    // only access if is not null
    val xLet = number?.let {
        // access it value, get a copy value at the time we call let func
        var num2 = it + 1
//        it = 1 it is val -> cannot assign, read-only
        num2// return last line, here is `num2` Int?
    } ?: 3 // return 3 as default value if null
    // let return the last line in it -> xLet is Unit
    // usage: when need a null check and access its value `it`(read-only, cannot change value)

}

private var i = 0

fun alsoFunction() {

    val xAlso = number?.also {
        // similar to let
        // also to do something else
        var num2 = it + 1
//        it = 1 it is val -> cannot assign, read-only
        // not return last line, return the object is called on
    } ?: 3
}

fun getSquaredI() = (i * i).also { i++ } // also to do something else

fun applyFunction() {
    // access object, check null, modify the object, when make a lot of changes
    // ex: when init Intent instance, adapter instance
    // usage: when have a lot of fields and want to modify, change many fields, apply these changes and return the object,
    // don't need to use `it` everytime, especially when have 20 fields -> useful
    val intent = Intent().apply {
        // return this, access to this object, dont need to use `it' everytime access the object
        putExtra("", "")
        putExtra("1", "1")
        action = ""
    } // return Intent instance with all the applies
}

fun runFunction() {
    // similar to apply: access this, modify object, but not return the object
    // similar to let: return last line
    val intent = Intent().run {
        putExtra("", "")
        putExtra("1", "1")
        action = ""
    } // return last line, intent is Unit

    val intent1 = Intent().run {
        putExtra("", "")
        putExtra("1", "1")
        action = ""
        this
    } // apply{} = run{} return this -> that's why apply{} used much
}

fun withFunction() {
    // similar to run: access this, modify object, return last line
    // cannot check null -> prefer using run
    // apply > run > with
    with(Intent()) {

    }
}
