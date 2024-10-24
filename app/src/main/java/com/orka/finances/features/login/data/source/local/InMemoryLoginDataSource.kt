package com.orka.finances.features.login.data.source.local

import com.orka.finances.features.login.data.models.UserCredentials
import com.orka.finances.features.login.data.source.LoginDataSource

class InMemoryLoginDataSource : LoginDataSource {
    override suspend fun getCredentials(username: String, password: String): UserCredentials? {
        return if(username == "admin" && password == "123")
            UserCredentials("token", "access")
        else null
    }
}