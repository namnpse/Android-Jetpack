package com.namnp.androidjetpack.mvi

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface MVI<UiState, UiEvent, SideEffect> {

    val uiState: StateFlow<UiState>

    val sideEffect: Flow<SideEffect>

    val uiEvent: SharedFlow<UiEvent>

    fun onEvent(newUiEvent: UiEvent)

    fun updateUiState(block: UiState.() -> UiState)

    fun updateUiState(newUiState: UiState)

    // Another option would to add the extension on BaseViewModel
    // ViewModel.emitSideEffect(effect: SideEffect) to make it easier to use if only using it in ViewModel
    fun CoroutineScope.emitSideEffect(effect: SideEffect)

}