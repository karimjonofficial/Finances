package com.orka.main

import com.orka.credentials.Credential

interface MainFsm {

    fun handle(event: MainEvent)

    fun checkCredentials(state: MainStates.Initial)
    fun setCredential(credential: Credential, state: MainStates.HasSingleton.UnAuthorized)
    fun unauthorize(state: MainStates.HasSingleton.HasCredential)

    fun initContainers(
        state: MainStates.HasSingleton.HasCredential.CreatingContainers,
        navigateToWarehouse: (Int) -> Unit,
        navigateToStockItem: (Int) -> Unit
    )
}