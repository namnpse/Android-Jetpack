package com.namnp.androidjetpack.practice

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.system.measureTimeMillis

fun main() {
    concurrentApproachWithWaitTime()
}

// Approach 1: Concurrent Approach with Wait Time
fun concurrentApproachWithWaitTime() {
    val time = measureTimeMillis {
        CoroutineScope(Dispatchers.Main).launch {
            var data1Response: String? = null
            var data2Response: String? = null
            var data3Response: String? = null
            val call1 = async { getAPICall1() }
            val call2 = async { getAPICall2() }
            val call3 = async { getAPICall3() }
            try {
                println("WAIT 1")
                data1Response = call1.await()
                println("WAIT 2")
                data2Response = call2.await()
                println("WAIT 3")
                data3Response = call3.await()
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
            processData(data1Response, data2Response, data3Response)
        }
    }
    println("Completed in $time ms")
}

fun processData(data1: String?, data2: String?, data3: String?) {
    println("processData: ${data1 + data2 + data3}")
}

suspend fun getAPICall1(): String {
    delay(200)
    println("done getAPICall1")
    return "API 1"
}

suspend fun getAPICall2(): String {
    delay(30)
    println("done getAPICall2")
    return "API 2"
}

suspend fun getAPICall3(): String {
    delay(10)
    println("done getAPICall3")
    return "API 3"
}

/*
Case 1: delay(2)
WAIT 1
done getAPICall1
WAIT 2
done getAPICall3
done getAPICall2
WAIT 3
processData: API 1API 2API 3
Completed in 95 ms
*/
/* Case 2: delay(200)
WAIT 1
done getAPICall3
done getAPICall2
done getAPICall1
WAIT 2
WAIT 3
processData: API 1API 2API 3
Completed in 99 ms
*/

suspend fun fetchData() = coroutineScope {
    val mergedResponse = listOf(
        async { getAPICall1() },
        async { getAPICall2() }
    )
    // combine call a list of APIs at a time.
    mergedResponse.awaitAll()
}

// Approach 2: Concurrent/ Parallel Approach with Thread Switching
fun approach2() {
    CoroutineScope(Dispatchers.IO).launch {
        withContext(Dispatchers.Default) {
            println("call getAPICall1")
            // wait 1 done -> start 2
            val apiResponse1 = getAPICall1()
            println("call getAPICall2")
            val apiResponse2 = getAPICall2()
//            if (apiResponse1.isSuccessful() && apiResponse2.isSuccessful() {
//
//            }
        }
    }
}
/* delay(200) in getApiCall1(), delay(30) in getAPICall2()
call getAPICall1
done getAPICall1
// wait 1 done -> start 2
call getAPICall2
done getAPICall2
Completed in 97 ms
*/

// Approach 3: Parallel Approach with Data Merging
fun getAPICall1UsingFlow() = flow<String> {
    delay(200)
    println("done getAPICall1UsingFlow")
    emit("API 1")
}
fun getAPICall2UsingFlow() = flow<String> {
    delay(20)
    println("done getAPICall2UsingFlow")
    emit("API 2")
}
fun approach3() {
    CoroutineScope(Dispatchers.IO).launch {
        getAPICall1UsingFlow()
            .zip(getAPICall2UsingFlow()) { data1, data2 ->
                return@zip data1 + data2
            }
            .flowOn(Dispatchers.IO)
            .catch { e ->

            }
            .collect { it ->
//                handleSuccessResponse
            }
    }
}

// NOTE
/*
when want to call multiple parallel calls with wait time, async-await Approach will be suitable.
When want an even more efficient approach with thread switching, withContext will be suitable.
And to stitch two responses together and perform some data manipulation, Zip operator approach is suitable.
*/
