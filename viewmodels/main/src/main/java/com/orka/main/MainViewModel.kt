package com.orka.main

import androidx.lifecycle.viewModelScope
import com.orka.core.CredentialsManager
import com.orka.core.SingleStateViewModel
import com.orka.core.UserInfoDataSource
import com.orka.credentials.Credential
import com.orka.info.UserInfo
import com.orka.unauthorizer.Unauthorizer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
    private val userInfoDataSource: UserInfoDataSource
) : SingleStateViewModel<AuthenticationState>(
    httpService = NullHttpService,
    initialState = AuthenticationState.Initial
), Unauthorizer, CredentialsManager {

    fun initUserInfo() {
        launch(Dispatchers.IO) {
            get()?.let { setStateAuthorized(it) } ?: setStateUnauthorized()
        }
    }

    private suspend fun updateUserInfo(it: UserInfo, credential: Credential) {
        userInfoDataSource.update(
            it.copy(token = credential.token, refresh = credential.refresh)
        )
    }

    override fun unauthorize() {
        viewModelScope.launch(Dispatchers.IO) {
            userInfoDataSource.unauthorize()
            setStateUnauthorized()
        }
    }

    private suspend fun getUserInfo() = userInfoDataSource.get()

    private fun setStateAuthorized(credential: Credential) {
        setState(AuthenticationState.Authorized(credential))
    }

    private fun setStateUnauthorized() {
        setState(AuthenticationState.UnAuthorized)
    }

    override suspend fun get(): Credential? {
        return getUserInfo()?.let {
            val (_, token, refresh) = it
            if (token?.isNotBlank() == true)
                Credential(token, refresh)
            else null
        }
    }

    override suspend fun set(credential: Credential) {

        val info = getUserInfo()
        if (info != null) {
            updateUserInfo(info, credential)
        } else {
            userInfoDataSource.add(
                UserInfo(
                    token = credential.token,
                    refresh = credential.refresh
                )
            )
        }
        setStateAuthorized(credential)
    }
}

