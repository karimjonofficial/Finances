package com.orka.finances.features.login.data.sources

import com.orka.finances.lib.data.credentials.Credential

interface LoginDataSource {
    suspend fun getCredential(username: String, password: String): Credential?
}