package com.orka.core

import com.orka.credentials.Credential

interface CredentialsDataSource {
    suspend fun get(username: String, password: String): Credential?
}