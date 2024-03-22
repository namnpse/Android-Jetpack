package com.namnp.androidjetpack.best_practice.error_handling.domain

interface AuthRepository {
    suspend fun register(password: String): Result<User, DataError.Network>
}

data class User(
    val id: Int,
    val name: String,
    val email: String,
    val phoneNumber: String,
)