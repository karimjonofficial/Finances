package com.orka.main

import com.orka.credentials.Credential

sealed class AuthenticationState {
    data object Initial : AuthenticationState()
    data object UnAuthorized : AuthenticationState()
    data class Authorized(val credential: Credential) : AuthenticationState()
}