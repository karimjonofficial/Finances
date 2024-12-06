package com.orka.finances.features.login.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.orka.finances.features.login.data.sources.LoginDataSource
import com.orka.finances.lib.data.credentials.Credentials
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginScreenViewModel(
    private val dataSource: LoginDataSource,
    private val setCredentials: (Credentials) -> Unit
) : ViewModel() {

    private val _uiState = MutableStateFlow<LoginScreenState>(LoginScreenState.Initial)
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
            } else {
                _uiState.value = LoginScreenState.Failed("No such users found")
                resetStateDelayed()
            }
        } catch(e: Exception) {
            _uiState.value = LoginScreenState.Failed("Check the internet connection")
            resetStateDelayed()
        }
    }

    private suspend fun resetStateDelayed() {
        delay(3000)
        resetState()
    }

    fun resetState() {
        if(_uiState.value is LoginScreenState.Failed) {
            _uiState.value = LoginScreenState.Initial
        }
    }
}