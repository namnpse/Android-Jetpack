package com.namnp.androidjetpack.mvi

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MVIDelegation<UiState, UiEvent, SideEffect> internal constructor(
    initialUiState: UiState
) : MVI<UiState, UiEvent, SideEffect> {

    private val _uiState = MutableStateFlow<UiState>(initialUiState)
    override val uiState: StateFlow<UiState>
        get() = _uiState.asStateFlow()

    private val  _uiEvent by lazy {  MutableSharedFlow<UiEvent>() }
    override val uiEvent: SharedFlow<UiEvent> by lazy { _uiEvent.asSharedFlow() }

    private val _sideEffect by lazy { Channel<SideEffect>() }
    override val sideEffect: Flow<SideEffect> by lazy { _sideEffect.receiveAsFlow() }


    override fun CoroutineScope.emitSideEffect(effect: SideEffect) {
        this.launch {
            _sideEffect.send(effect)
        }
    }

    override fun updateUiState(newUiState: UiState) {
        _uiState.update { newUiState }
    }

    override fun updateUiState(block: UiState.() -> UiState) {
        _uiState.update(block)
    }

    override fun onEvent(newUiEvent: UiEvent) {
//        _uiEvent.emit(newUiEvent)
    }
}

fun <UiState, UiEvent, SideEffect> mvi(
    initialUiState: UiState
): MVI<UiState, UiEvent, SideEffect> = MVIDelegation(initialUiState)