package com.orka.main

import com.orka.containers.SingletonContainer
import com.orka.credentials.Credential

sealed interface MainStates {
    fun handle(event: MainEvent, fsm: MainFsm)

    data object Initial : MainStates {

        override fun handle(event: MainEvent, fsm: MainFsm) {
            if(event is MainEvent.Initialize) fsm.checkCredentials(this)
        }
    }

    sealed class HasSingleton(open val singletonContainer: SingletonContainer) : MainStates {

        data class UnAuthorized(
            override val singletonContainer: SingletonContainer
        ) : HasSingleton(singletonContainer) {

            override fun handle(event: MainEvent, fsm: MainFsm) {
                if(event is MainEvent.Authorize) {
                    fsm.setCredential(event.credential, this)
                }
            }
        }

        sealed class HasCredential(
            open val credential: Credential,
            override val singletonContainer: SingletonContainer
        ) : HasSingleton(singletonContainer) {

            data class CreatingContainers(
                override val credential: Credential,
                override val singletonContainer: SingletonContainer
            ) : HasCredential(credential, singletonContainer) {

                override fun handle(event: MainEvent, fsm: MainFsm) {
                    if (event == MainEvent.UnAuthorize)
                        fsm.unauthorize(this)
                    else if (event is MainEvent.InitContainers)
                        fsm.initContainers(this, event.navigateToWarehouse, event.navigateToStockProduct)
                }
            }

            data class HasContainers(
                override val credential: Credential,
                override val singletonContainer: SingletonContainer,
                val scopedContainer: SingletonContainer.ScopedContainer,
                val transientContainer: SingletonContainer.ScopedContainer.TransientContainer
            ) : HasCredential(credential, singletonContainer) {

                override fun handle(event: MainEvent, fsm: MainFsm) {
                    if (event == MainEvent.UnAuthorize) fsm.unauthorize(this)
                }
            }

        }
    }
}