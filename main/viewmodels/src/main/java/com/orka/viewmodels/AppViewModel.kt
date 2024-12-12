package com.orka.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.orka.data.UserInfo
import com.orka.data.UserInfoDataSource
import com.orka.lib.models.Credential
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
                val (_, token, refresh) = info
                if(token?.isNotBlank() == true) {
                    val credential = Credential(token = token, refresh = refresh ?: "")
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
                userInfoDataSource.add(UserInfo(token = credential.token, refresh = credential.refresh))
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

    private suspend fun getUserInfo() = userInfoDataSource.get()

    private fun setStateAuthorized(credential: Credential) {
        _uiState.value = AuthenticationState.Authorized(credential)
    }

    private fun setStateUnauthorized() {
        _uiState.value = AuthenticationState.UnAuthorized
    }
}

