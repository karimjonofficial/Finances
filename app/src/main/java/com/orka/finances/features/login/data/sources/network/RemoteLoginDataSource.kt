package com.orka.finances.features.login.data.sources.network

import com.orka.finances.features.login.data.sources.LoginDataSource
import com.orka.finances.lib.data.credentials.Credential

class RemoteLoginDataSource(private val apiService: LoginApiService) : LoginDataSource {
    override suspend fun getCredential(username: String, password: String): Credential? {
        return apiService.getCredential(username, password)
    }
}