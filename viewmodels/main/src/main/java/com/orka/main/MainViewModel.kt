package com.orka.main

import androidx.lifecycle.viewModelScope
import com.orka.core.BaseViewModelWithState
import com.orka.core.CredentialsManager
import com.orka.core.UserInfoDataSource
import com.orka.credentials.Credential
import com.orka.info.UserInfo
import com.orka.unauthorizer.Unauthorizer
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainViewModel(
    private val userInfoDataSource: UserInfoDataSource
) : BaseViewModelWithState<AuthenticationState>(AuthenticationState.Initial), Unauthorizer,
    CredentialsManager {

    fun initUserInfo() {
        get()?.let { setStateAuthorized(it) } ?: setStateUnauthorized()
    }

    private suspend fun updateUserInfo(it: UserInfo, credential: Credential) {
        userInfoDataSource.update(
            it.copy(token = credential.token, refresh = credential.refresh)
        )
    }

    override fun unauthorize() {
        viewModelScope.launch {
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

    override fun get(): Credential? {
        return runBlocking {
            getUserInfo()?.let {
                val (_, token, refresh) = it
                if (token?.isNotBlank() == true)
                    Credential(token, refresh)
                else null
            }
        }
    }

    override fun set(credential: Credential) {
        launch {
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
}

