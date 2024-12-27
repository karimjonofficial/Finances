package com.orka.main

import com.orka.credentials.Credential

interface MainFsm {

    fun handle(event: MainEvent)

    fun initialize(state: MainStates.Initial)
    fun setCredential(credential: Credential, state: MainStates.WithSingleton.UnAuthorized)
    fun unauthorize(state: MainStates.WithSingleton.WithCredential)

    fun initContainers(
        state: MainStates.WithSingleton.WithCredential.Initializing,
        navigateToWarehouse: (Int) -> Unit,
        navigateToStockItem: (Int) -> Unit
    )
}