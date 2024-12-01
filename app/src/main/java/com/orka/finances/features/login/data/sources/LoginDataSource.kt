package com.orka.finances.features.login.data.sources

import com.orka.finances.lib.data.credentials.Credentials

interface LoginDataSource {
    suspend fun getCredentials(username: String, password: String): Credentials?
}