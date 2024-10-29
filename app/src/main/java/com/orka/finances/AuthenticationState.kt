package com.orka.finances

import com.orka.finances.lib.data.CredentialsDataSource

sealed class AuthenticationState {
    data object Initial : AuthenticationState()
    data object UnAuthorized : AuthenticationState()

    data class Authorized(
        val credentialsDataSource: CredentialsDataSource
    ) : AuthenticationState()
}