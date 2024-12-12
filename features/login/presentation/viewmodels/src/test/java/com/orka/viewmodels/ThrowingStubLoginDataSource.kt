package com.orka.viewmodels

import com.orka.base.LoginDataSource
import com.orka.lib.models.Credential

class ThrowingStubLoginDataSource : LoginDataSource {
    override suspend fun getCredential(username: String, password: String): Credential? {
        throw Exception()
    }
}
