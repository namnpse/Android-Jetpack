package com.namnp.androidjetpack.kotlin.annotation

import java.util.Date

//@JvmField: access the fields of a Kotlin class from Java code without using any getters and setters
data class Meeting(@JvmField val name: String, val date: Date = Date())

fun main() {
    // call in Kotlin
    val meeting = Meeting("Meeting", Date())
    val name = meeting.name // (get field `name` directly)

    // call in Java
//    Session session = new Session("Session", new Date());
//    String name = session.name; // compilation error (cannot call session.name directly)

}
