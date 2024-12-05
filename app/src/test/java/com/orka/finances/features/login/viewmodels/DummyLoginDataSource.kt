package com.orka.finances.features.login.viewmodels

import com.orka.finances.features.login.data.sources.LoginDataSource
import com.orka.finances.lib.data.credentials.Credentials

class DummyLoginDataSource : LoginDataSource {
    override suspend fun getCredentials(username: String, password: String): Credentials? {
        return null
    }
}