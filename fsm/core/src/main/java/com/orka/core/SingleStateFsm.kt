package com.orka.core

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class SingleStateFsm
    <TState : FsmState<TEvent, TState, TFsm>,
        TEvent : FsmEvent,
            TFsm : SingleStateFsm<TState, TEvent, TFsm>>(initialState: TState) : ViewModel() {

    private var _uiState: MutableStateFlow<FsmState<TEvent, TState, TFsm>> =
        MutableStateFlow(initialState)
    val uiState = _uiState.asStateFlow()

    internal fun setState(state: TState) {
        launch {
            beforeSetState(state)
            this._uiState.value = state
            afterSetState(state)
        }
    }

    fun handle(event: TEvent) {
        launch {
            onHandling(event)
            _uiState.value.handle(event, this@SingleStateFsm as TFsm)
            onHandled(event)
        }
    }

    protected open fun beforeSetState(state: TState) {}
    protected open fun afterSetState(state: TState) {}
    protected open fun onHandling(event: TEvent) {}
    protected open fun onHandled(event: TEvent) {}
}