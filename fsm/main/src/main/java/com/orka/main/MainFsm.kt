package com.orka.main

import com.orka.credentials.Credential

interface MainFsm {

    fun handle(event: AppEvents)

    fun checkCredentials(state: AppStates.Initial)
    fun setCredential(credential: Credential, state: AppStates.HasSingleton.UnAuthorized)
    fun unauthorize(state: AppStates.HasSingleton.HasCredential)

    fun initContainers(
        state: AppStates.HasSingleton.HasCredential.CreatingContainers,
        navigateToWarehouse: (Int) -> Unit,
        navigateToProduct: (Int) -> Unit
    )
}