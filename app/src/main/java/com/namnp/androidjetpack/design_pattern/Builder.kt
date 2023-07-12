package com.namnp.androidjetpack.design_pattern

// Use case: Room.DatabaseBuilder(), Retrofit.Builder()

// Java style
class Hamburger private constructor(
    val cheese: Boolean,
    val beef: Boolean,
    val onions: Boolean,
) {
    class Builder {
        private var cheese: Boolean = true
        private var beef: Boolean = false
        private var onions: Boolean = false

        fun cheese(value: Boolean) = apply { cheese = value }

        fun beef(value: Boolean) = apply { beef = value }

        fun onions(value: Boolean) = apply { onions = value }

        fun build() = Hamburger(cheese, beef, onions)
    }
}

// Kotlin style
//data class Hamburger (
//    val cheese: Boolean = true,
//    val beef: Boolean = true,
//    val onions: Boolean = false,
//)

fun main() {
    // Kotlin style
//    val hamburger = Hamburger(cheese = false, beef = true, onions = true)

    // Java style
    val hamburger1 = Hamburger.Builder()
        .cheese(false)
        .beef(true)
        .onions(true)
        .build()

    val hamburger2 = Hamburger.Builder().apply {
        cheese(false)
        beef(true)
        onions(true)
    }.build()
}
