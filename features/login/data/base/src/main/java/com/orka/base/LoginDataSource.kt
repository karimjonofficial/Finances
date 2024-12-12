package com.orka.base

import com.orka.lib.models.Credential

interface LoginDataSource {
    suspend fun getCredential(username: String, password: String): Credential?
}