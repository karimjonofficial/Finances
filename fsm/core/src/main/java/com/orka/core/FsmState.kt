package com.orka.core

abstract class FsmState {
    protected abstract suspend fun process(event: FsmEvent, fsm: SingleStateFsm): FsmState

    internal suspend fun handle(event: FsmEvent, fsm: SingleStateFsm) {
        fsm.setState(process(event, fsm))
    }
}