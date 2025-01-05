package com.orka.login

import com.orka.core.CredentialManager
import com.orka.core.CredentialsDataSource
import com.orka.core.HttpService
import com.orka.core.SingleStateViewModel
import kotlinx.coroutines.delay

class LoginScreenViewModel(
    private val dataSource: CredentialsDataSource,
    private val credentialManager: CredentialManager,
    httpService: HttpService
) : SingleStateViewModel<LoginScreenState>(httpService, LoginScreenState.Initial) {

    fun login(username: String, password: String) {
        if (username.isNotBlank() && password.isNotBlank()) {
            launch { authorize(username, password) }
        }
    }

    private fun resetState() {
        if (uiState.value is LoginScreenState.Failed) {
            setState(LoginScreenState.Initial)
        }
    }

    private fun authorize(username: String, password: String) {
        request(
            request = {
                val credential = dataSource.get(username, password)
                if (credential != null) {
                    credentialManager.setCredential(credential)
                } else {
                    setState(LoginScreenState.Failed("No such users found"))
                    resetStateDelayed()
                }
            },
            onException = { e ->
                setState(LoginScreenState.Failed(e.message.toString()))
                launch { resetStateDelayed() }
            }
        )
    }

    private suspend fun resetStateDelayed() {
        delay(3000)
        resetState()
    }
}