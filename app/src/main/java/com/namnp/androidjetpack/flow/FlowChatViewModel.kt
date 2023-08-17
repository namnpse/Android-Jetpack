package com.namnp.androidjetpack.flow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.namnp.androidjetpack.coroutines.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class FlowChatViewModel : ViewModel() {
    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users = _users.asStateFlow()

    //    private val _localUser = MutableStateFlow<User?>(null)
//    val localUser = _localUser.asStateFlow()
//
//    fun onUserJoined(user: User) {
//        _users.update { it + user }
//        // every time _user update -> have to update _localUser as well
//        if(user.id.toString() == "local") {
//            _localUser.update { user }
//        }
//    }
//
//    // every time _user update -> have to update _localUser as well
//    fun onUserInfoUpdate(user: User) {
//        _users.update {
//            it.map { currUser ->
//                if(currUser.id == user.id) user else currUser
//            }
//        }
//      every time _user update -> have to update _localUser as well -> vulnerable to bug, race condition
//        if(user.id.toString() == "local") {
//            _localUser.update { user }
//        }
//    }
// every time _user update -> have to update _localUser as well -> vulnerable to bug, race condition
//    fun onUserLeft(user: User) {}
//    fun onUserTyping(user: User) {}
//    ...
//    FIX: get derived state from other flow
    val localUser = users.map { users ->
        users.find { it.id.toString() == "local" }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null) // must have this line, if not, nothing happens
    // cache latest value to view model, execute block of code only when there is a subscribers to localUser

    fun onUserJoined(user: User) {
        _users.update { it + user }
    }

    // every time _user update -> have to update _localUser as well
    fun onUserInfoUpdate(user: User) {
        _users.update {
            it.map { currUser ->
                if (currUser.id == user.id) user else currUser
            }
        }
    }
}