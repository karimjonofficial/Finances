package com.orka.core

import com.orka.credentials.Credential

interface CredentialsManager {
    suspend fun get(): Credential?
    suspend fun set(credential: Credential)
}