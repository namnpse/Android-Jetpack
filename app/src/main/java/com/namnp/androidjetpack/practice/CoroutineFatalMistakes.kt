package com.namnp.androidjetpack.practice

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.net.HttpRetryException
import kotlin.random.Random

fun main() {

}

// Mistake 1: call sequential coroutine
fun avoidCallingSequentialCoroutine() = runBlocking {
    getListOfUsername(arrayListOf<String>("1", "2"))
}

// call sequential apis in for loop -> BAD
//suspend fun getListOfUsername(userIds: List<String>): List<String> {
//    val listUsername = mutableListOf<String>()
//    for (id in userIds) {
//        val userName = getUserNameById(id)
//        listUsername.add(userName)
//    }
//    return listUsername
//}
//-> FIX:
suspend fun getListOfUsername(userIds: List<String>): List<String> {
    val listUsername = mutableListOf<Deferred<String>>()
    coroutineScope {
        for (id in userIds) {
            val userName = async {
                getUserNameById(id)
            } // run independent from other coroutines, if delay -> not affect
            listUsername.add(userName)
        }
    }
    return listUsername.awaitAll()
}

suspend fun getUserNameById(id: String): String {
    delay(1000)
    return "Namnpse"
}

// Mistake 2: Not check a coroutine is canceled
suspend fun checkCoroutineCanceled() {
    val job = CoroutineScope(Dispatchers.Default).launch {
        var random = Random.nextInt(100_000)
//        while (random != 50_000) { // not check job cancel (isActive) -> BAD
        while (random != 50_000 && isActive) { // approach 1: check job cancel (isActive)
            ensureActive() // approach 2: ensureActive(), if not -> throw CancellationException
            random = Random.nextInt(100_000)
        }
    }
    delay(500)
    job.cancel()
}

// Mistake 3: Not calling coroutine in Main safety
suspend fun callCoroutineMainSafety(): Result<String> {
    // do network call in Main thread -> not main safety -> BAD, should run on IO dispatcher
    val result = doNetworkCall()
    return if (result == "Success") Result.success(result) else Result.failure(Exception())
}

suspend fun doNetworkCall(): String {
    // do network call in Main thread -> not main safety -> BAD, should run on IO dispatcher
//    -> FIX:
    return withContext(Dispatchers.IO) {
        delay(2000)
        if (Random.nextBoolean()) "Success" else "Failure"
    }
}

// Mistake 4: caught all exceptions including CancellationException -> parent coroutine is NOT notified about the exception
suspend fun riskyTask() {
//    try {
//        delay(1000)
//        println("Result: ${1/0}") // simulate exception here
//    }catch (e: Exception) { // all exceptions are caught here (including CancellationException)
//        println("Opp, something went wrong!!!")
//    }
//    -> FIX:
//    1. only catch specific exceptions (HttpException)
//    2. check CancellationException in catch block and then rethrow it

    try {
        delay(1000)
        println("Result: ${1 / 0}") // simulate exception here
    } catch (e: HttpRetryException) { // only catch HttpRetryException
    } catch (e: Exception) { // all exceptions are caught here (including CancellationException)
        println("Opp, something went wrong!!!")
        if (e is CancellationException) {
            throw e // propagate up the CancellationException to parent coroutine
        }
    }
}

// Mistake 5: Expose suspend fun in ViewModel to UI
class UserViewModel: ViewModel() {

    // ViewModel should NOT expose suspend fun for UI
    // BAD
//    suspend fun getUserInfo(): String {
//        delay(1000)
//        return "Namnpse"
//    }
    // GOOD
    fun getUserInfo(): String {
        // normally do job by viewModelScope
        // rarely do job outside of viewModelScope -> if want -> use normal coroutineScope (have to handle cancellation,...)
        val normalCoroutineScope = CoroutineScope(Dispatchers.IO).launch {}
        viewModelScope.launch {
            delay(1000)
        }
        return "Namnpse"
    }
}

class UserActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        val viewModel = UserViewModel()
        lifecycleScope.launch {
            viewModel.getUserInfo()
        }
//        Assume: click the button -> make network call. Then user rotate the device -> lifecycleScope cancel getUserInfo (suspend fun) -> NOT GOOD
//        -> FIX: should bind the job to ViewModel scope, not Activity Scope -> continue doing the job
    }
}