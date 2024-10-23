package com.orka.finances.features.login.data.source

import com.orka.finances.features.login.data.models.UserCredentials

interface LoginDataSource {
    fun getCredentials(username: String, password: String): UserCredentials?
}