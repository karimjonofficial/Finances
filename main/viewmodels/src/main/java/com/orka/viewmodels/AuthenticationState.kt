package com.orka.viewmodels

import com.orka.lib.models.Credential

sealed class AuthenticationState {
    data object Initial : AuthenticationState()
    data object UnAuthorized : AuthenticationState()

    data class Authorized(
        val credential: Credential
    ) : AuthenticationState()
}