package com.namnp.androidjetpack.best_practice.error_handling.data

import com.namnp.androidjetpack.best_practice.error_handling.domain.AuthRepository
import com.namnp.androidjetpack.best_practice.error_handling.domain.DataError
import com.namnp.androidjetpack.best_practice.error_handling.domain.Result
import com.namnp.androidjetpack.best_practice.error_handling.domain.User
import retrofit2.HttpException

class AuthRepositoryImpl: AuthRepository {

    override suspend fun register(password: String): Result<User, DataError.Network> {
        return try {
            // API call logic
            val user = User(1, "Nam  Jr", "namnpse@gmail.com", "098 123 4433")
            Result.Success(user)
        } catch(e: HttpException) {
            when(e.code()) {
                404 -> Result.Error(DataError.Network.NOT_FOUND)
                408 -> Result.Error(DataError.Network.REQUEST_TIMEOUT)
                413 -> Result.Error(DataError.Network.PAYLOAD_TOO_LARGE)
                503 -> Result.Error(DataError.Network.INTERNAL_SERVER)
                else -> Result.Error(DataError.Network.UNKNOWN)
            }
        }
    }
}