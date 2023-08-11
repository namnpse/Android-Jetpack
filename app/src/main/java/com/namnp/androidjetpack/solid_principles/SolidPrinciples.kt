package com.namnp.androidjetpack.solid_principles

import java.lang.Math.PI

fun main() {

}

// 1.    Single Responsibility Principle (SRP)

// BAD
class UserService {
    fun authenticateUser(username: String, password: String) {
        // Authentication logic
    }
    fun getUserData(userId: String): UserData {
        // Retrieve user data from the server
        return UserData()
    }
}

class UserData {}

//-> GOOD:
class UserAuthenticationService {
    fun authenticateUser(username: String, password: String) {
        // Authentication logic
    }
}
class UserDataService {
    fun getUserData(userId: String): UserData {
        // Retrieve user data from the server
        return UserData()
    }
}

// 2. Open-Closed Principle (OCP)

// BAD
data class Payment(val type: String)
class PaymentProcessor {
    fun processPayment(payment: Payment) {
        if (payment.type == "CreditCard") {
            // Code for credit card payment processing
        } else if (payment.type == "PayPal") {
            // Code for PayPal payment processing
        } else if (payment.type == "Bitcoin") {
            // Code for Bitcoin payment processing
        }
        // ...more payment types
    }
}
//-> GOOD:

interface PaymentOCP {
    fun startPaying()
}

class CreditPayment: PaymentOCP {
    override fun startPaying() {
        // process pay by credit
    }
}

class PaypalPayment: PaymentOCP {
    override fun startPaying() {
        // process pay by Paypal
    }
}

class BitcoinPayment: PaymentOCP {
    override fun startPaying() {
        // process pay by Bitcoin
    }
}
class PaymentOCPProcessor() {
    fun processPayment(paymentMethod: PaymentOCP) {
        paymentMethod.startPaying()
    }
}
// -> can add new payment processors without modifying the existing code


// 3. Liskov Substitution Principle (LSP)
// objects of a superclass should be replaceable with objects of its subclasses without affecting the correctness of the program
// ensures that inheritance relationships are properly implemented, without causing unexpected behavior or breaking the code.
// BAD:
//open class Shape {
//    open fun calculateArea(): Double {
//        return 0.0
//    }
//}
//
//class Rectangle : Shape() {
//    override fun calculateArea(): Double {
//        return width * height
//    }
//}
//
//class Circle : Shape() {
//    override fun calculateArea(): Double {
//        return PI * radius * radius
//    }
//}

// -> GOOD:
// + make Shape and calculateArea() func abstract
abstract class Shape {
    abstract fun calculateArea(): Double
}

// + parameters width, height, and radius are now explicitly passed to the subclasses
class Rectangle(private val width: Double, private val height: Double) : Shape() {
    override fun calculateArea(): Double {
        return width * height
    }
}
class Circle(private val radius: Double) : Shape() {
    override fun calculateArea(): Double {
        return PI * radius * radius
    }
}

fun printArea(shape: Shape) {
    println("Area: ${shape.calculateArea()}")
}

//4. Interface Segregation Principle (ISP):
// BAD -> must implement all functions, hard to reuse
//interface Worker {
//    fun work()
//    fun eat()
//    fun sleep()
//}
//
//class Developer: Worker {
//    override fun work() {
//        // code how a developer works
//    }
//
//    override fun eat() {
//        // code how a developer eats
//    }
//
//    override fun sleep() {
//        // code how a developer sleeps
//    }
//}

// GOOD: split the large Worker interface into smaller interfaces (Workable, Eatable, Sleepable)
// the Doctor class can implement only the interfaces it needs, preventing unnecessary dependencies
interface Workable {
    fun work()
}

interface Eatable {
    fun eat()
}

interface Sleepable {
    fun sleep()
}

class Developer : Workable, Eatable, Sleepable {
    override fun work() {
        // code how a developer works
    }

    override fun eat() {
        // code how a developer eats
    }

    override fun sleep() {
        // code how a developer sleeps
    }
}

class Doctor: Workable {
    override fun work() {
        // implement only 1 function eat()
    }
}

// 5. Dependency Inversion Principle (DIP):
// high-level modules should not depend on low-level modules
// Both should depend on abstractions (should depend on interface, not on concrete implementation)
// -> loose coupling and make unit testing easier

// BAD:
//class UserService {
//    private val userRepository = UserRepository()
//
//    fun getUser(userId: String): User {
//        return userRepository.getUser(userId)
//    }
//}

// GOOD:
//class UserService(private val userRepository: UserRepository) {
//    fun getUser(userId: String): User {
//        return userRepository.getUser(userId)
//    }
//}