package com.orka.finances.features.login.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.orka.finances.features.login.data.source.LoginDataSource

class LoginScreenViewModel(
    private val dataSource: LoginDataSource,
    private val passScreen: () -> Unit
) : ViewModel() {

    fun authorize(username: String, password: String) {
        val credentials = dataSource.getCredentials(username, password)

        if(credentials != null) {
            passScreen()
        }
    }
}