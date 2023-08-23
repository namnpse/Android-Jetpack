package com.namnp.androidjetpack.compose.sharing_data.util.persistent

data class Session(
    val user: User,
    val token: String,
    val expiresAt: Long
)

data class User(
    val firstName: String,
    val lastName: String,
    val email: String
)