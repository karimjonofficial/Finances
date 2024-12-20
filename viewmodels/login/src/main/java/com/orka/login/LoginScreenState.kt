package com.orka.login

sealed class LoginScreenState {
    data object Initial : LoginScreenState()
    data class Failed(val message: String) : LoginScreenState()
}