package com.orka.finances.features.login.data.source.network

import com.orka.finances.features.login.data.models.UserCredentials
import com.orka.finances.features.login.data.source.LoginDataSource

class RemoteLoginDataSource(private val apiService: LoginApiService) : LoginDataSource {
    override suspend fun getCredentials(username: String, password: String): UserCredentials? {
        return try { apiService.access(username, password) } catch (e: Exception) { null }
    }
}