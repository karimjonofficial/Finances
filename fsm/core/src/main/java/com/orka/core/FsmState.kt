package com.orka.core

abstract class FsmState
<TEvent: FsmEvent,
        TState: FsmState<TEvent, TState, TFsm>,
        TFsm: SingleStateFsm<TState, TEvent, TFsm>> {

    protected abstract suspend fun process(event: TEvent, fsm: TFsm): TState

    internal suspend fun handle(event: TEvent, fsm: TFsm) {
        fsm.setState(process(event, fsm))
    }
}