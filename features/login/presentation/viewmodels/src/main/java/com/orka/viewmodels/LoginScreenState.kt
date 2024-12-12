package com.orka.viewmodels

sealed class LoginScreenState {
    data object Initial : LoginScreenState()
    data class Failed(val message: String) : LoginScreenState()
}
