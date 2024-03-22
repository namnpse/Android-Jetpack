package com.namnp.androidjetpack.best_practice.error_handling.domain

interface DataError: Error {
    enum class Network: DataError {
        NOT_FOUND,
        REQUEST_TIMEOUT,
        TOO_MANY_REQUESTS,
        NO_INTERNET,
        PAYLOAD_TOO_LARGE,
        SERVER_ERROR,
        SERIALIZATION,
        INTERNAL_SERVER,
        UNKNOWN
    }

    enum class Local: DataError {
        IO_ERROR,
        DISK_FULL
    }
}