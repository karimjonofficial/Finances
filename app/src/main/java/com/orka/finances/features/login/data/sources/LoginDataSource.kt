package com.orka.finances.features.login.data.sources

import com.orka.finances.features.login.data.models.UserCredentials

interface LoginDataSource {
    suspend fun getCredentials(username: String, password: String): UserCredentials?
}