package com.orka.composables

import com.orka.base.LoginDataSource
import com.orka.lib.models.Credential

internal class InMemoryLoginDataSource : LoginDataSource {
    override suspend fun getCredential(username: String, password: String): Credential? {
        return if(username == "admin" && password == "123")
            Credential("token", "access")
        else null
    }
}