package com.orka.finances.features.login.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.orka.finances.features.login.data.sources.LoginDataSource
import com.orka.finances.lib.data.Credentials
import kotlinx.coroutines.launch

class LoginScreenViewModel(
    private val dataSource: LoginDataSource,
    private val setCredentials: (Credentials) -> Unit
) : ViewModel() {

    fun login(username: String, password: String) {
        if(username.isNotBlank() && password.isNotBlank())
            viewModelScope.launch {
                val credentials = dataSource.getCredentials(username, password)
                if (credentials != null) { setCredentials(credentials) }
            }
    }
}