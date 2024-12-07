package com.orka.finances.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.orka.finances.lib.data.credentials.Credential
import com.orka.finances.lib.data.info.UserInfo
import com.orka.finances.lib.data.info.UserInfoDataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AppViewModel(
    private val userInfoDataSource: UserInfoDataSource
) : ViewModel() {
    private val _uiState = MutableStateFlow<AuthenticationState>(AuthenticationState.Initial)
    val uiState = _uiState.asStateFlow()

    fun initUserInfo() {
        viewModelScope.launch {
            val info = getUserInfo()
            if(info != null) {
                if(info.token?.isNotBlank() == true) {
                    val credential = Credential(info.token, info.refresh ?: "")
                    setStateAuthorized(credential)
                } else { setStateUnauthorized() }
            } else { setStateUnauthorized() }
        }
    }

    fun setCredentials(credential: Credential) {
        viewModelScope.launch {
            val info = getUserInfo()
            if(info != null) {
                updateUserInfo(info, credential)
            } else {
                userInfoDataSource.insert(UserInfo(token = credential.token, refresh = credential.refresh))
            }
            setStateAuthorized(credential)
        }
    }

    private suspend fun updateUserInfo(it: UserInfo, credential: Credential) {
        userInfoDataSource.update(
            it.copy(token = credential.token, refresh = credential.refresh)
        )
    }

    fun unauthorize() {
        viewModelScope.launch {
            userInfoDataSource.unauthorize()
            _uiState.value = AuthenticationState.UnAuthorized
        }
    }

    private suspend fun getUserInfo() = userInfoDataSource.select()

    private fun setStateAuthorized(credential: Credential) {
        _uiState.value = AuthenticationState.Authorized(credential)
    }

    private fun setStateUnauthorized() {
        _uiState.value = AuthenticationState.UnAuthorized
    }
}

