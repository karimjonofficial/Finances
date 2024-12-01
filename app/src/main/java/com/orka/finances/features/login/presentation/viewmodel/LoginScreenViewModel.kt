package com.orka.finances.features.login.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.orka.finances.features.login.data.sources.LoginDataSource
import com.orka.finances.lib.data.credentials.Credentials
import com.orka.finances.lib.log.Log
import kotlinx.coroutines.launch

class LoginScreenViewModel(
    private val dataSource: LoginDataSource,
    private val setCredentials: (Credentials) -> Unit
) : ViewModel() {

    fun login(username: String, password: String) {
        if(username.isNotBlank() && password.isNotBlank()) {
            viewModelScope.launch { authorize(username, password) }
        }
    }

    private suspend fun authorize(username: String, password: String) {
        val credentials = dataSource.getCredentials(username, password)
        if (credentials != null) {
            setCredentials(credentials)
        } else {
            log("Credentials", "Not exists")
        }
    }

    private fun log(tag: String, message: String) {
        Log(".LoginScreenViewModel.$tag", message)
    }
}