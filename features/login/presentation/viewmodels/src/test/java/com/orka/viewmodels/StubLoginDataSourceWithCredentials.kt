package com.orka.viewmodels

import com.orka.base.LoginDataSource
import com.orka.lib.CREDENTIAL
import com.orka.lib.models.Credential

class StubLoginDataSourceWithCredentials : LoginDataSource {
    override suspend fun getCredential(username: String, password: String): Credential {
        return CREDENTIAL
    }
}