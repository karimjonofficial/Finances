package com.orka.finances.features.login.data.sources.network

import com.orka.finances.lib.data.UserCredentials
import com.orka.finances.features.login.data.sources.LoginDataSource

class RemoteLoginDataSource(
    private val apiService: LoginApiService,
    private val setCredentials: suspend (UserCredentials) -> Unit
) : LoginDataSource {
    override suspend fun getCredentials(username: String, password: String): UserCredentials? {
        return try {
            val credentials = apiService.getCredentials(username, password)
            credentials?.let { setCredentials(it) }
            return credentials
        } catch (e: Exception) { null }
    }
}