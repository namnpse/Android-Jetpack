package com.namnp.androidjetpack.coroutines

import android.view.Display
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.namnp.androidjetpack.coroutines.model.User
import com.namnp.androidjetpack.coroutines.model.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CoroutineViewModel : ViewModel() {

    private var usersRepository = UserRepository()

    // normal way (without viewModelScope)
//    private val job = Job()
//    private val customScope = CoroutineScope(Dispatchers.IO + job)

    // C1:
//    var users: MutableLiveData<List<User>?> = MutableLiveData()
//    fun getUsers() {
// using viewModelScope -> auto cancel coroutine via lifecycle, don't need to cancel manually on onCleared()
//        viewModelScope.launch {
//            var result: List<User>? = null
//            withContext(Dispatchers.IO) {
//                result = usersRepository.getUsers()
//            }
//            users.value = result
//        }
//    }

    // C2: using live data directly
    var users = liveData(Dispatchers.IO) {
        val result = usersRepository.getUsers()
        emit(result)
    }

    // normal way (without viewModelScope), need to cancel job manually
//    override fun onCleared() {
//        super.onCleared()
//        job.cancel()
//    }

    // unstructured concurrency example
    suspend fun getTotal(): Int {
        var total = 0
        CoroutineScope(Dispatchers.IO).launch {
            delay(1000)
            total = 10
        }
        val deffered = CoroutineScope(Dispatchers.IO).async {
            delay(2000)
            return@async 20
        }
        // work with async, but cannot handle exceptions
        return total + deffered.await()
    }
    // Output: 20
    // Reason: Unstructured Concurrency does NOT guarantee to complete all tasks of suspending function before it returns

    // Structured concurrency example
    suspend fun getTotalStructuredConcurrency(): Int {
        var total = 0
        var deffered: Deferred<Int>
        coroutineScope {
            launch(Dispatchers.IO) {
                delay(1000)
                total = 10
            }
            deffered = async(Dispatchers.IO) {
                delay(2000)
                return@async 20
            }
        }
        return total + deffered.await()
    }
    // Output: 30
    /*
    Structured Concurrency GUARANTEES to:
      + complete all the work started by coroutines, within the child scope, before the return of the suspending function.
      + notify the caller exception thrown
      + can cancel all tasks inside the coroutine will be cancelled
      */
    //This coroutineScope waits for the child coroutines to complete
}