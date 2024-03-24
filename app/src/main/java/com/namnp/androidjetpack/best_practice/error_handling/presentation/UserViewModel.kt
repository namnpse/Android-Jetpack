package com.namnp.androidjetpack.best_practice.error_handling.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.namnp.androidjetpack.best_practice.error_handling.domain.AuthRepository
import com.namnp.androidjetpack.best_practice.error_handling.domain.Result
import com.namnp.androidjetpack.best_practice.error_handling.domain.User
import com.namnp.androidjetpack.best_practice.error_handling.domain.UserDataValidatorUseCase
import com.namnp.androidjetpack.best_practice.string_resource_view_model.UiText
import kotlinx.coroutines.launch
import com.namnp.androidjetpack.best_practice.error_handling.presentation.UserContract.*
import com.namnp.androidjetpack.mvi.MVI
import com.namnp.androidjetpack.mvi.mvi

class UserViewModel(
    private val authRepository: AuthRepository,
    private val userDataValidatorUseCase: UserDataValidatorUseCase,
) : ViewModel(), MVI<UserState, UserEvent, UserEffect> by mvi(initialUiState()) {

    // comment these since using MVI Delegate pattern
/*    private val _userState = MutableStateFlow(UserState())
    val userState: StateFlow<UserState> = _userState.asStateFlow()

    private val _events = Channel<UserEffect>()
    val events = _events.receiveAsFlow()*/

    override fun onEvent(newUiEvent: UserEvent) {
        when(newUiEvent) {
            is UserEvent.OnRegisterClick -> {
                registerUser(newUiEvent.password)
            }
        }
    }

    private fun registerUser(password: String) {
        when(val result = userDataValidatorUseCase.validatePassword(password)) {
            is Result.Error -> {
                when(result.error) {
                    UserDataValidatorUseCase.PasswordError.PASSWORD_TOO_SHORT -> {} // show message
                    UserDataValidatorUseCase.PasswordError.EXCEED_MAXIMUM_LENGTH -> {}
                    UserDataValidatorUseCase.PasswordError.NO_UPPERCASE -> {}
                    UserDataValidatorUseCase.PasswordError.NO_DIGIT -> Unit
                }
            }
            is Result.Success -> {
                // show register successfully
            }
        }

        viewModelScope.launch {
            when(val result = authRepository.register(password)) {
                is Result.Error -> {
                    val errorMsg = result.error.asUiText() // or result.asErrorUiText()
//                    _events.send(UserEffect.ShowError(errorMsg))
                    emitSideEffect(UserEffect.ShowError(errorMsg)) // using MVI Delegate
                }

                is Result.Success -> {
                    val user: User = result.data // getting user from server
                    // display user info
//                    _userState.update { _userState.value.copy(user = user) }
                    updateUiState { copy(user = user) } // using MVI Delegate
                }
            }
        }
    }
}

fun initialUiState() = UserState(User(0, "", "", ""))

// MVI pattern
interface UserContract {

    data class UserState(
        val user: User,
    )

    sealed interface UserEvent {
        data class OnRegisterClick(val password: String) : UserEvent
    }

    sealed interface UserEffect {
        data class ShowError(val error: UiText) : UserEffect
    }
}