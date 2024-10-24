package com.orka.finances.features.login.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.orka.finances.features.login.data.source.LoginDataSource
import kotlinx.coroutines.launch

class LoginScreenViewModel(
    private val dataSource: LoginDataSource,
    private val passScreen: () -> Unit
) : ViewModel() {

    fun login(username: String, password: String) {
        viewModelScope.launch {
            val credentials = dataSource.getCredentials(username, password)
            if (credentials != null) { passScreen() }
        }
    }
}