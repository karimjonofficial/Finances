package com.orka.finances.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.orka.finances.lib.data.credentials.Credentials
import com.orka.finances.lib.data.credentials.local.LocalCredentialsDataSource
import com.orka.finances.lib.data.info.UserInfoDataSource
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
            if(info != null) {
                if(info.token?.isNotBlank() == true) {
                    val credentials = Credentials(info.token, info.refresh ?: "")
                    setStateAuthorized(LocalCredentialsDataSource(credentials))
                } else { setStateUnauthorized() }
            } else { setStateUnauthorized() }
        }
    }

    fun setCredentials(credentials: Credentials) {
        //TODO Reorganize and redesign it. When the info is null it should get info using credentials and set the info.
        // When state is authorized then no nullable info allowed.
        viewModelScope.launch {
            val info = getUserInfo()
            info?.let {
                userInfoDataSource.update(
                    it.copy(token = credentials.token, refresh = credentials.refresh)
                )
            }
            setStateAuthorized(LocalCredentialsDataSource(credentials))
        }
    }

    fun unauthorize() {
        viewModelScope.launch {
            userInfoDataSource.unauthorize()
            _uiState.value = AuthenticationState.UnAuthorized
        }
    }

    private suspend fun getUserInfo() = userInfoDataSource.select()

    private fun setStateAuthorized(credentialsDataSource: LocalCredentialsDataSource) {
        _uiState.value = AuthenticationState.Authorized(credentialsDataSource)
    }

    private fun setStateUnauthorized() {
        _uiState.value = AuthenticationState.UnAuthorized
    }
}

