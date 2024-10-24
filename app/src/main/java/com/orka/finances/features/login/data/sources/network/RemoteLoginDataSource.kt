package com.orka.finances.features.login.data.sources.network

import com.orka.finances.features.login.data.models.UserCredentials
import com.orka.finances.features.login.data.sources.LoginDataSource

class RemoteLoginDataSource(private val apiService: LoginApiService) : LoginDataSource {
    override suspend fun getCredentials(username: String, password: String): UserCredentials? {
        return try { apiService.getCredentials(username, password) } catch (e: Exception) { null }
    }
}