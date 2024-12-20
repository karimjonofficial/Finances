package com.orka.core

import com.orka.credentials.Credential

interface CredentialsManager {
    fun get(): Credential?
    fun set(credential: Credential)
}