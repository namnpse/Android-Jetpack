package com.namnp.androidjetpack.compose.best_pratice

sealed class UIEvent{
    data class ShowMessage(val message:String): UIEvent()
}
