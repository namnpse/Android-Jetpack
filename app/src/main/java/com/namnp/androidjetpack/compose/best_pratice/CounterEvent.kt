package com.namnp.androidjetpack.compose.best_pratice

sealed class CounterEvent{
       data class ValueEntered(val value : String) : CounterEvent()
       object CountButtonClicked : CounterEvent()
       object ResetButtonClicked : CounterEvent()
}
