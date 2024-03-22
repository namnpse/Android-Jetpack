package com.namnp.androidjetpack.best_practice.error_handling.domain

// NORMAL APPROACH: cannot handle different types of error since message is just a String
sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T?): Resource<T>(data)
    class Error<T>(message: String?, data: T? = null): Resource<T>(data, message) // still pass data in case error getting remote value and we want to pass local value as default
}