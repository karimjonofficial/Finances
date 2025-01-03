package com.orka.main

import com.orka.core.CredentialsManager
import com.orka.core.SingleStateViewModel
import com.orka.core.UserInfoDataSource
import com.orka.credentials.Credential
import com.orka.di.SingletonContainer
import com.orka.info.UserInfo
import com.orka.unauthorizer.Unauthorizer
import kotlinx.coroutines.Dispatchers

class MainViewModel(
    private val userInfoDataSource: UserInfoDataSource
) : SingleStateViewModel<MainStates>(
    httpService = NullHttpService,
    initialState = MainStates.Initial
), Unauthorizer, CredentialsManager, MainFsm {

    override fun handle(event: MainEvent) {
        launch { uiState.value.handle(event, this) }
    }

    override fun initialize(state: MainStates.Initial) {
        launch(Dispatchers.IO) {
            val credential = get()
            if (credential != null) setStateWithSingleton(credential)
            else
                setState(MainStates.WithSingleton.UnAuthorized(SingletonContainer(this, this)))
        }
    }

    private fun setStateWithSingleton(credential: Credential) {
        launch {
            val singleton = SingletonContainer(this, this)
            setState(
                MainStates.WithSingleton.WithCredential.Initializing(credential, singleton)
            )
        }
    }

    override fun setCredential(
        credential: Credential,
        state: MainStates.WithSingleton.UnAuthorized
    ) {
        launch {
            setState(
                MainStates.WithSingleton.WithCredential.Initializing(
                    credential,
                    state.singletonContainer
                )
            )
        }
    }

    override fun unauthorize(state: MainStates.WithSingleton.WithCredential) {
        launch {
            setState(MainStates.WithSingleton.UnAuthorized(state.singletonContainer))
            unauthorize()
        }
    }

    override fun initContainers(
        state: MainStates.WithSingleton.WithCredential.Initializing,
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
                MainStates.WithSingleton.WithCredential.WithContainers(
                    credential = state.credential,
                    singletonContainer = state.singletonContainer,
                    scopedContainer = scoped,
                    transientContainer = transient
                )
            )
        }
    }

    override fun unauthorize() {
        launch(Dispatchers.IO) {
            userInfoDataSource.unauthorize()
        }
    }

    override suspend fun get(): Credential? {
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

    override suspend fun set(credential: Credential) {
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
}