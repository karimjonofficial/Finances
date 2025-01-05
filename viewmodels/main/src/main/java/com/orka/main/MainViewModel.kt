package com.orka.main

import com.orka.containers.SingletonContainer
import com.orka.core.CredentialManager
import com.orka.core.SingleStateViewModel
import com.orka.core.UserInfoDataSource
import com.orka.credentials.Credential
import com.orka.info.UserInfo
import kotlinx.coroutines.Dispatchers

class MainViewModel(
    private val userInfoDataSource: UserInfoDataSource
) : SingleStateViewModel<MainStates>(
    httpService = NullHttpService,
    initialState = MainStates.Initial
), CredentialManager, MainFsm {

    override fun handle(event: MainEvent) {
        launch { uiState.value.handle(event, this) }
    }

    override fun checkCredentials(state: MainStates.Initial) {
        launch(Dispatchers.IO) {
            val credential = getCredential()
            if (credential != null) setStateWithSingleton(credential)
            else setState(
                MainStates.HasSingleton.UnAuthorized(
                    SingletonContainer(
                        unauthorize = { handle(MainEvent.UnAuthorize) },
                        credentialManager = this
                    )
                )
            )
        }
    }

    override fun setCredential(
        credential: Credential,
        state: MainStates.HasSingleton.UnAuthorized
    ) {
        launch {
            setState(
                state = MainStates.HasSingleton.HasCredential.CreatingContainers(
                    credential = credential,
                    singletonContainer = state.singletonContainer
                )
            )
        }
    }

    override fun unauthorize(state: MainStates.HasSingleton.HasCredential) {
        launch {
            setState(MainStates.HasSingleton.UnAuthorized(state.singletonContainer))
            clearCredential()
        }
    }

    override fun initContainers(
        state: MainStates.HasSingleton.HasCredential.CreatingContainers,
        navigateToWarehouse: (Int) -> Unit,
        navigateToStockItem: (Int) -> Unit
    ) {
        launch {
            val scoped = state.singletonContainer.scopedContainer(
                credential = state.credential,
                navigateToWarehouse = navigateToWarehouse,
            )

            val transient = scoped.transientContainer(navigateToStockItem)

            setState(
                MainStates.HasSingleton.HasCredential.HasContainers(
                    credential = state.credential,
                    singletonContainer = state.singletonContainer,
                    scopedContainer = scoped,
                    transientContainer = transient
                )
            )
        }
    }

    private fun clearCredential() {
        launch(Dispatchers.IO) {
            userInfoDataSource.unauthorize()
        }
    }

    override suspend fun getCredential(): Credential? {
        val info = userInfoDataSource.get()
        if (info == null) {
            return null
        } else {
            val token = info.token
            return if (token?.isNotEmpty() == true) {
                Credential(token, info.refresh)
            } else null
        }
    }

    override suspend fun setCredential(credential: Credential) {
        val info = userInfoDataSource.get()
        if (info == null) {
            val new = UserInfo(1, credential.token, credential.refresh)
            launch(Dispatchers.IO) { userInfoDataSource.add(new) }
            launch { setStateWithSingleton(credential) }
        } else {
            val new = info.copy(token = credential.token, refresh = credential.refresh)
            launch(Dispatchers.IO) { userInfoDataSource.update(new) }
            launch { setStateWithSingleton(credential) }
        }
    }

    private fun setStateWithSingleton(credential: Credential) {
        launch {
            val singleton = SingletonContainer(this) { handle(MainEvent.UnAuthorize) }
            setState(
                MainStates.HasSingleton.HasCredential.CreatingContainers(credential, singleton)
            )
        }
    }

}