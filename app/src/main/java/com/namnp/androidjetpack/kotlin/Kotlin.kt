package com.namnp.androidjetpack.kotlin

import android.os.Build
import java.io.File

fun main() {
    reifiedTypeParameter()
    typealiasClass()
    genericClass()
    requireAndCheck()
    delegateInKotlin()
    dataClass()
    sealedClass()
    higherOrderFunction()
}

// reified
private fun reifiedTypeParameter() {
    val list = listOf("one", "two", "three")
    list.printElement()
}

inline fun <reified T> List<T>.printElement() {
    println(T::class.simpleName)
}

// typealias
typealias EmployeeId = String

data class Employee(val id: EmployeeId, val name: String)

private fun typealiasClass() {
    val employee = Employee("1234", "Namnpse")
    println("Employee ${employee.name} has ID ${employee.id}.") // Employee Namnpse has ID 1234.
}

// generic class
class Box<T>(var contents: T)

private fun genericClass() {

    val boxOfInts = Box<Int>(42)
    val boxOfString = Box<String>("Hello namnpse")

    println("Box of ints contains: ${boxOfInts.contents}")
    println("Box of string contains: ${boxOfString.contents}")
}

// `use` function
// execute a block of code on a resource and automatically close the resource after finishing the block of code
private fun useFunction() {
    File("namnpse.txt").bufferedReader().use { reader ->
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            reader.lines().forEach {
                println(it)
            }
        }
    }
}

// require, check

fun divide(a: Int, b: Int) {
    require(b != 0) { "Divisor must not be zero" }
    println(a / b)
}

fun checkState(state: Boolean) {
    check(state) { "Invalid state" }
}

private fun requireAndCheck() {
    divide(10, 2) // 5
    divide(10, 0) // IllegalArgumentException: Divisor must not be zero

    checkState(true) // no exception
    checkState(false) // IllegalStateException: Invalid state
}

// delegate
// a design pattern that allows objects to delegate certain responsibilities to other objects, without having to implement those responsibilities themselves
// using the by keyword
private fun delegateInKotlin() {
    val b = BaseImpl(10)
    // all calls to print() on Derived will be forwarded to b
    Derived(b).print()
}

interface Base {
    fun print()
}

class BaseImpl(private val x: Int) : Base {
    override fun print() {
        println(x)
    }
}

class Derived(b: Base) : Base by b

// data class
// is designed to hold data
// automatically generates methods such as toString(), equals(), hashCode(), and copy() based on the class properties
// can implement Builder design pattern easier than in Java
// a form of overloading compare to Java
// must have at least 1 property

data class Person(val name: String, val age: Int)

private fun dataClass() {
    val person1 = Person("Alice", 30)
    val person2 = Person("Bob", 40)

    println(person1) // "Person(name=Alice, age=30)"
    println(person1 == person2) // false
    val person3 = person1.copy(name = "Charlie")
    println(person3) // "Person(name=Charlie, age=30)"
}

// sealed class
// can only be extended within the same file -> restrict hierarchy of classes
sealed class Shape
class Circle(val radius: Double) : Shape()
class Rectangle(val width: Double, val height: Double) : Shape()

private fun printArea(shape: Shape) {
    when (shape) {
        is Circle -> println("Area of circle: ${Math.PI * shape.radius * shape.radius}")
        is Rectangle -> println("Area of rectangle: ${shape.width * shape.height}")
    }
}

private fun sealedClass() {
    val circle = Circle(5.0)
    val rectangle = Rectangle(5.0, 10.0)

    printArea(circle) // "Area of circle: 78.53981633974483"
    printArea(rectangle) // "Area of rectangle: 50.0"
}


// lambda expression {} (using curly braces)
// define a func in a concise and readable way, can be passed as parameter to a higher-order func
private fun lambdaExpression() {
    val square = { x: Int -> x * x }
    val result = square(5)
}

// higher-order function


fun performOperator(x: Int, operator: (Int) -> Int): Int {
    return operator(x)
}

private fun higherOrderFunction() {
    val operator: (Int, (Int) -> Int) -> Int = { x, f -> f(x) }
    val sumOperator: (Int, Int, (Int, Int) -> Int) -> Int = { a, b, sum -> sum(a, b) }
    val squared = operator(5) { x -> x * x }
    val sum = sumOperator(6, 8) { a, b -> a + b } // 14
    print("Squared=$squared")
    val a = performOperator(5) { x -> x + 5 }
}



