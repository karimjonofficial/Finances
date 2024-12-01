package com.orka.finances.features.login.data.sources.network

import com.orka.finances.lib.data.credentials.Credentials
import com.orka.finances.features.login.data.sources.LoginDataSource

class RemoteLoginDataSource(
    private val apiService: LoginApiService
) : LoginDataSource {
    override suspend fun getCredentials(username: String, password: String): Credentials? {
        return apiService.getCredentials(username, password)
    }
}