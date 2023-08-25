package com.namnp.androidjetpack.practice.event_bus

import android.annotation.SuppressLint

// EventBus listen by object type, not event name
//1. Benefit:
// + not dispatch event only, can add additional data to event, e.g: MessageEvent(1, "Hello, Namnpse")
//2. Disadvantage:
// + have to define a class for each event
// + listen String type -> have to listen multiple String value
@SuppressLint("CheckResult")
fun main() {

    // listen only for String event
    EventBusUsingRxJava.listen(String::class.java).subscribe {
        println("String event: $it")
    }

    // listen for MessageEvent only
    EventBusUsingRxJava.listen(MessageEvent::class.java).subscribe {
        println("MessageEvent event: $it")
    }

    // listen for Int event only
//    RxBusUsingRxJava.listen(Int::class.java).subscribe {
    EventBusUsingRxJava.listen(Number::class.java).subscribe {// use Number instead of Int/Double
        println("Number event: $it")
    }

    EventBusUsingRxJava.listen(Enum::class.java).subscribe {
        println("Enum event: ${it.name}")
    }

    EventBusUsingRxJava.listen(RxEvent::class.java).subscribe {event ->
        when(event) {
            is RxEvent.LOGIN -> {
                println("RxEvent LOGIN with username ${event.username} password ${event.password}")
            }
            is RxEvent.LOGOUT -> {
                println("RxEvent LOGOUT")
            }
        }
    }

    // Publish events
    EventBusUsingRxJava.publish(MessageEvent(1, "Hello, Namnpse"))
    EventBusUsingRxJava.publish("Namnpse")
    EventBusUsingRxJava.publish(1234)
    EventBusUsingRxJava.publish(1234.6)
    EventBusUsingRxJava.publish(Enum.A1)
    EventBusUsingRxJava.publish(Enum.A2)
    EventBusUsingRxJava.publish(RxEvent.LOGOUT)
    EventBusUsingRxJava.publish(RxEvent.LOGIN(username = "namnpse", password = "1234"))
}

data class MessageEvent(val action: Int, val message: String)

enum class Enum {
    A1, A2
}

sealed interface RxEvent {
    data class LOGIN(val username: String, val password: String): RxEvent
    object LOGOUT: RxEvent
}


