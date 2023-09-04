package com.namnp.androidjetpack.design_pattern


// establishes a one-to-many relationship between objects.
// when state of one object changes, all observers are notified and updated automatically
// achieve loose coupling and real-time communication between objects
interface Observer {
    fun onUpdate(data: Any)
}

interface Subject {
    fun add(observer: Observer)
    fun remove(observer: Observer)
    fun notifyObservers(data: Any)
}

class DataSubject: Subject {

    private val observers: MutableList<Observer> = mutableListOf()

    override fun add(observer: Observer) {
        observers.add(observer)
    }

    override fun remove(observer: Observer) {
        observers.remove(observer)
    }

    override fun notifyObservers(data: Any) {
        observers.forEach { observer ->
            observer.onUpdate(data)
        }
    }
}

class DataObserver: Observer {
    override fun onUpdate(data: Any) {
        // update when receive new data
    }
}

fun main() {
    val subject: Subject = DataSubject()
    val observer1: Observer = DataObserver()
    val observer2: Observer = DataObserver()
    val observer3: Observer = DataObserver()
    subject.add(observer1)
    subject.add(observer2)
    subject.add(observer3)

//    ...

    subject.notifyObservers("New Data")
}

