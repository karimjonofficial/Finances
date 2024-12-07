package com.orka.finances.features.login.data.sources.local

import com.orka.finances.features.login.data.sources.LoginDataSource
import com.orka.finances.lib.data.credentials.Credential

class InMemoryLoginDataSource : LoginDataSource {
    override suspend fun getCredential(username: String, password: String): Credential? {
        return if(username == "admin" && password == "123")
            Credential("token", "access")
        else null
    }
}