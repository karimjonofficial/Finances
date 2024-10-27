package com.orka.finances.features.login.data.sources

import com.orka.finances.lib.data.UserCredentials

interface AuthenticationDataSource {
    suspend fun authorize(username: String, password: String): UserCredentials?
}