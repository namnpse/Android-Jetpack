package com.namnp.androidjetpack.flow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.namnp.androidjetpack.coroutines.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class ChatViewModel : ViewModel() {

    private val isLoggedIn = MutableStateFlow<Boolean>(true)
    private val messages = MutableStateFlow<List<String>>(emptyList())
    private val users = MutableStateFlow<List<User>>(emptyList())

    // (isLoggedIn, messages, users) like list dependencies in React (useEffect, useState)
    // every time (isLoggedIn, messages, users) change -> chatState is updated
    val chatState = combine(isLoggedIn, messages, users) { isLoggedIn, messages, users ->
        if (isLoggedIn) {
            ChatState(
                userPreviews = users.map { curUser ->
                    UserPreview(
                        user = curUser,
                        lastMessage = messages.maxByOrNull { it }
                    )
                },
                titleHeader = users.firstOrNull()?.name ?: "Chat"
            )
        } else null
    } // Nothing happens, have to add this line of code below
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)

}

// NOTE:
//1. can use a lot of methods for derived state in ViewModel like map, combine, filter, reduce, zip, fold, flatmap, etc as per the use-case.
//2.  using  state.value  in operators like combine, suggest using that state in the dependents list similar useEffect with useState in react.

data class ChatState(
    val userPreviews: List<UserPreview>,
    val titleHeader: String,
)

data class UserPreview(
    val user: User,
    val lastMessage: String?,
)

data class ChatMessage(
    val user: User,
    val message: String,
    val time: Long,
)