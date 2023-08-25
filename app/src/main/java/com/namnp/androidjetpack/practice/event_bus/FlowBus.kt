package com.namnp.androidjetpack.practice.event_bus

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onSubscription
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


enum class AppEvent {
    LOGOUT;
}

sealed interface FlowEvent {
    data class LOGIN(val username: String, val password: String): FlowEvent
    object LOGOUT: FlowEvent
}

class FlowBus {
    private val _events = MutableSharedFlow<FlowEvent>()
    val events = _events.asSharedFlow()

    suspend fun emitEvent(event: FlowEvent) = _events.emit(event)
}

fun main() {
    runBlocking {
        handleFlowBus()
    }
}

private val scope = CoroutineScope(Dispatchers.Default)

suspend fun handleFlowBus() = runBlocking {

    val eventBus = FlowBus()

    // listen LOGOUT event
    // C1
//    eventBus.events
//        .filter { event -> event == FlowEvent.LOGOUT }
//        .collectLatest {
//            println("LOGGING OUT...")
//        }

    // C2
    launch {
        println("AAAA")
        eventBus.events
//            .filter { event -> event is FlowEvent.LOGIN }
            .onSubscription {
                println("onSubscription")
            }
            .collectLatest { event ->
                println("EVENT: $event")
                when(event) {
                    is FlowEvent.LOGOUT -> {
                        println("LOGGING OUT...")
                    }
                    is FlowEvent.LOGIN -> {
                        println("LOGIN with username: ${event.username} ${event.password}")
                    }
                }
            }

    }
    delay(1000)
    eventBus.emitEvent(FlowEvent.LOGIN(username = "namnpse", password = "1234"))

}
