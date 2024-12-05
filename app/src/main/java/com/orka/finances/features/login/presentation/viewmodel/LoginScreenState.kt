package com.orka.finances.features.login.presentation.viewmodel

data class LoginScreenState(
    val username: String = "",
    val password: String = "",
    val remember: Boolean = false,
    val passwordVisible: Boolean = false
)
