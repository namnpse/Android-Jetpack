package com.namnp.androidjetpack.design_pattern

// add additional behavior or functionality to an object dynamically, without changing its underlying structure.
// useful when want to extend the functionality of an object without subclassing.
class Decorator {
}

interface View {
    fun draw()
}

class TextView: View {
    override fun draw() {
        // draw text view
    }
}

class BorderDecorator(private val view: View): View {
    override fun draw() {
        // Decorate the view with a border
        view.draw()
        // Add border drawing logic
    }
}

fun main() {
    val textView = TextView()
    val decoratedTextView = BorderDecorator(textView)
    decoratedTextView.draw()
}