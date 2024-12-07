package com.orka.finances.features.login.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.orka.finances.features.login.data.sources.LoginDataSource
import com.orka.finances.lib.data.credentials.Credential
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginScreenViewModel(
    private val dataSource: LoginDataSource,
    private val setCredential: (Credential) -> Unit
) : ViewModel() {

    private val _uiState = MutableStateFlow<LoginScreenState>(LoginScreenState.Initial)
    val uiState = _uiState.asStateFlow()

    fun login(username: String, password: String) {
        if(username.isNotBlank() && password.isNotBlank()) {
            viewModelScope.launch { authorize(username, password) }
        }
    }

    fun resetState() {
        if(_uiState.value is LoginScreenState.Failed) {
            _uiState.value = LoginScreenState.Initial
        }
    }

    private suspend fun authorize(username: String, password: String) {
        try {
            val credential = dataSource.getCredential(username, password)
            if (credential != null) {
                setCredential(credential)
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
}