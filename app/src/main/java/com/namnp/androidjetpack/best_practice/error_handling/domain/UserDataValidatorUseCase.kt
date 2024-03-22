package com.namnp.androidjetpack.best_practice.error_handling.domain

class UserDataValidatorUseCase {

    fun validatePassword(password: String): Result<Unit, PasswordError> {
        if(password.length < 8) {
            return Result.Error(PasswordError.PASSWORD_TOO_SHORT)
        } else if(password.length > 100) {
            return Result.Error(PasswordError.EXCEED_MAXIMUM_LENGTH)
        }

        val hasUppercaseChar = password.any { it.isUpperCase() }
        if(!hasUppercaseChar) {
            return Result.Error(PasswordError.NO_UPPERCASE)
        }

        val hasDigit = password.any { it.isDigit() }
        if(!hasDigit) {
            return Result.Error(PasswordError.NO_DIGIT)
        }

        return Result.Success(Unit)

    }

    enum class PasswordError: Error {
        PASSWORD_TOO_SHORT,
        EXCEED_MAXIMUM_LENGTH,
        NO_UPPERCASE,
        NO_DIGIT,
    }
}