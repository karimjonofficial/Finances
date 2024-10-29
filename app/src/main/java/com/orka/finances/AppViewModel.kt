package com.orka.finances

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.orka.finances.lib.data.Credentials
import com.orka.finances.lib.data.UserInfoDataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AppViewModel(
    private val userInfoDataSource: UserInfoDataSource
) : ViewModel() {
    private val _uiState = MutableStateFlow<AuthenticationState>(AuthenticationState.Initial)
    val uiState = _uiState.asStateFlow()

    fun initialize() {
        viewModelScope.launch {
            val info = getUserInfo()
            info?.let {
                if(it.token?.isNotBlank() == true) {
                    setCredentials(Credentials(it.token, it.refresh ?: ""))
                } else {
                    _uiState.value = AuthenticationState.UnAuthorized
                }
            }
        }
    }

    fun setCredentials(credentials: Credentials) {
        viewModelScope.launch {
            val info = getUserInfo()
            info?.let {
                userInfoDataSource.update(
                    it.copy(token = credentials.token, refresh = credentials.refresh)
                )
            }
            val credentialsDataSource = LocalCredentialsDataSource(credentials)
            _uiState.value = AuthenticationState.Authorized(credentialsDataSource)
        }
    }

    fun unauthorize() {
        viewModelScope.launch {
            userInfoDataSource.unauthorize()
            _uiState.value = AuthenticationState.UnAuthorized
        }
    }

    private suspend fun getUserInfo() = userInfoDataSource.select()
}

