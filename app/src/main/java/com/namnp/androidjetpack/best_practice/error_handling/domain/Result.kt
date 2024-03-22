package com.namnp.androidjetpack.best_practice.error_handling.domain

// use AppError to differentiate Error interface and Result.Error below
typealias AppError = Error

// BETTER APPROACH: can differentiate types of Error (DataError.Network, DataError.Local, PasswordError,...), all implement Error interface
sealed interface Result<out D, out E : AppError> {
    data class Success<out D, out E : AppError>(val data: D) : Result<D, E>
    data class Error<out D, out E : AppError>(val error: E) : Result<D, E>
}