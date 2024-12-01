package com.orka.finances.features.login.data.sources.local

import com.orka.finances.lib.data.credentials.Credentials
import com.orka.finances.features.login.data.sources.LoginDataSource

class InMemoryLoginDataSource : LoginDataSource {
    override suspend fun getCredentials(username: String, password: String): Credentials? {
        return if(username == "admin" && password == "123")
            Credentials("token", "access")
        else null
    }
}