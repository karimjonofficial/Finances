package com.orka.main

import com.orka.containers.SingletonContainer
import com.orka.credentials.Credential

sealed interface AppStates {
    fun handle(event: AppEvents, fsm: MainFsm)

    data object Initial : AppStates {

        override fun handle(event: AppEvents, fsm: MainFsm) {
            if(event is AppEvents.Initialize) fsm.checkCredentials(this)
        }
    }

    sealed class HasSingleton(open val singletonContainer: SingletonContainer) : AppStates {

        data class UnAuthorized(
            override val singletonContainer: SingletonContainer
        ) : HasSingleton(singletonContainer) {

            override fun handle(event: AppEvents, fsm: MainFsm) {
                if(event is AppEvents.Authorize) {
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

                override fun handle(event: AppEvents, fsm: MainFsm) {
                    if (event == AppEvents.UnAuthorize)
                        fsm.unauthorize(this)
                    else if (event is AppEvents.InitContainers)
                        fsm.initContainers(this, event.navigateToWarehouse, event.navigateToStockProduct)
                }
            }

            data class HasContainers(
                override val credential: Credential,
                override val singletonContainer: SingletonContainer,
                val scopedContainer: SingletonContainer.ScopedContainer
            ) : HasCredential(credential, singletonContainer) {

                override fun handle(event: AppEvents, fsm: MainFsm) {
                    if (event == AppEvents.UnAuthorize) fsm.unauthorize(this)
                }
            }

        }
    }
}