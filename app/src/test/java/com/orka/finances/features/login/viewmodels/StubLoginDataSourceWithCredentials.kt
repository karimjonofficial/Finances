package com.orka.finances.features.login.viewmodels

import com.orka.finances.CREDENTIAL
import com.orka.finances.features.login.data.sources.LoginDataSource
import com.orka.finances.lib.data.credentials.Credential

class StubLoginDataSourceWithCredentials : LoginDataSource {
    override suspend fun getCredential(username: String, password: String): Credential {
        return CREDENTIAL
    }
}