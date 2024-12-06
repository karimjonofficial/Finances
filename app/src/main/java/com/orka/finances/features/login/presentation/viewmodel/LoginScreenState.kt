package com.orka.finances.features.login.presentation.viewmodel

sealed class LoginScreenState {
    data object Initial : LoginScreenState()
    data class Failed(val message: String) : LoginScreenState()
}
