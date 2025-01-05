package com.orka.core

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class SingleStateFsm(initialState: FsmState) : ViewModel() {

    private var _uiState: MutableStateFlow<FsmState> = MutableStateFlow(initialState)
    val uiState = _uiState.asStateFlow()

    internal fun setState(state: FsmState) {
        launch {
            beforeSetState(state)
            this._uiState.value = state
            afterSetState(state)
        }
    }

    fun handle(event: FsmEvent) {
        launch {
            onHandling(event)
            _uiState.value.handle(event, this)
            onHandled(event)
        }
    }

    protected open fun beforeSetState(state: FsmState) {}
    protected open fun afterSetState(state: FsmState) {}
    protected open fun onHandling(event: FsmEvent) {}
    protected open fun onHandled(event: FsmEvent) {}
}