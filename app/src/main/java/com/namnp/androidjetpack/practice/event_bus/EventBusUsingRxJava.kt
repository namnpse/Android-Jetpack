package com.namnp.androidjetpack.practice.event_bus

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject

object EventBusUsingRxJava {

    private val publisher = PublishSubject.create<Any>()

    fun publish(event: Any) {
        publisher.onNext(event)
    }

    fun <T : Any> listen(eventType: Class<T>): Observable<T> = publisher.ofType(eventType)
}