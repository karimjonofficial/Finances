package com.orka.finances.ui

import com.orka.finances.lib.data.credentials.CredentialsDataSource

sealed class AuthenticationState {
    data object Initial : AuthenticationState()
    data object UnAuthorized : AuthenticationState()

    data class Authorized(
        val credentialsDataSource: CredentialsDataSource
    ) : AuthenticationState()
}