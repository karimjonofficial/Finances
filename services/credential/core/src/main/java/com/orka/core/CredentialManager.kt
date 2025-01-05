package com.orka.core

import com.orka.credentials.Credential

interface CredentialManager {
    suspend fun getCredential(): Credential?
    suspend fun setCredential(credential: Credential)
}