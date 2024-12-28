package com.orka.main

import com.orka.credentials.Credential
import com.orka.di.SingletonContainer

sealed interface MainStates {
    fun handle(event: MainEvent, fsm: MainFsm)

    data object Initial : MainStates {

        override fun handle(event: MainEvent, fsm: MainFsm) {
            if(event is MainEvent.Initialize) fsm.initialize(this)
        }
    }

    sealed class WithSingleton(open val singletonContainer: SingletonContainer) : MainStates {

        data class UnAuthorized(
            override val singletonContainer: SingletonContainer
        ) : WithSingleton(singletonContainer) {

            override fun handle(event: MainEvent, fsm: MainFsm) {
                if(event is MainEvent.Authorize) {
                    fsm.setCredential(event.credential, this)
                }
            }
        }

        sealed class WithCredential(
            open val credential: Credential,
            override val singletonContainer: SingletonContainer
        ) : WithSingleton(singletonContainer) {

            data class Initializing(
                override val credential: Credential,
                override val singletonContainer: SingletonContainer
            ) : WithCredential(credential, singletonContainer) {

                override fun handle(event: MainEvent, fsm: MainFsm) {
                    if (event == MainEvent.UnAuthorize) fsm.unauthorize(this)
                    else if (event is MainEvent.InitContainers) {
                        fsm.initContainers(this, event.navigateToWarehouse, event.navigateToStockProduct)
                    }
                }
            }

            data class WithContainers(
                override val credential: Credential,
                override val singletonContainer: SingletonContainer,
                val scopedContainer: SingletonContainer.ScopedContainer,
                val transientContainer: SingletonContainer.ScopedContainer.TransientContainer
            ) : WithCredential(credential, singletonContainer) {
                override fun handle(event: MainEvent, fsm: MainFsm) {
                    if (event == MainEvent.UnAuthorize) fsm.unauthorize(this)
                }
            }
        }
    }
}