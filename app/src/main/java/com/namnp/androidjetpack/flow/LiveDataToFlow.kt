package com.namnp.androidjetpack.flow

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class LiveDataToFlow:ViewModel() {
    // C1:
    val playlists = MutableLiveData<Int>()

    init {
        viewModelScope.launch {
            getPlaylist()
                .collect {
                    it.fold(
                        { data ->
                            playlists.value = data
                        },
                        { error ->
                            // show error
                        }
                    )

                    it.isSuccess
                    it.isFailure
                    it.getOrDefault(0)
                    it.getOrNull()
                    it.onFailure {  }
                    it.onSuccess {  }
                }
        }
    }

    // C2: more concise
    // import
    // def lifecycle_version = ""2.4.0""
    //implementation ""androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version""
    val playlists2 = liveData<Result<Int>> {
//        emit(1) // accept value T
        emitSource(getPlaylist().asLiveData()) // accept LiveData<T> value
    }

    fun getPlaylist(): Flow<Result<Int>> = flow {
        (1..100).forEach {
            if (it != 80)
                emit(Result.success(it))
            else emit(Result.failure(Exception("Invalid number")))
        }
    }
}