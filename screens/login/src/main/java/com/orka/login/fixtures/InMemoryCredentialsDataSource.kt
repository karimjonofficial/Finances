package com.orka.login.fixtures

import com.orka.core.CredentialsDataSource
import com.orka.credentials.Credential

internal class InMemoryCredentialsDataSource : CredentialsDataSource {

    override suspend fun get(username: String, password: String): Credential? {
        return if (username == "admin" && password == "123")
            Credential("token", "access")
        else null
    }
}