package com.orka.finances.features.login.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.orka.finances.features.login.data.sources.LoginDataSource
import com.orka.finances.lib.data.credentials.Credentials
import com.orka.finances.lib.log.Log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginScreenViewModel(
    private val dataSource: LoginDataSource,
    private val setCredentials: (Credentials) -> Unit
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginScreenState())
    val uiState = _uiState.asStateFlow()

    fun login(username: String, password: String) {
        if(username.isNotBlank() && password.isNotBlank()) {
            viewModelScope.launch { authorize(username, password) }
        }
    }

    private suspend fun authorize(username: String, password: String) {
        try {
            val credentials = dataSource.getCredentials(username, password)
            if (credentials != null) {
                setCredentials(credentials)
                log("Credentials", credentials.token)
            } else {
                log("NoCredentials", "Not exists")
            }
        } catch(e:Exception) {

        }
    }

    private fun log(tag: String, message: String) {
        Log(".LoginScreenViewModel.$tag", message)
    }
}