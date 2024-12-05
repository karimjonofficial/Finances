package com.orka.finances.features.login.data.sources.network

import com.orka.finances.CREDENTIAL
import com.orka.finances.PASSWORD
import com.orka.finances.USERNAME
import com.orka.finances.lib.data.credentials.Credentials

class MockLoginApiService : LoginApiService {
    override suspend fun getCredentials(username: String, password: String): Credentials? {
        return if(username == USERNAME && password == PASSWORD) CREDENTIAL else null
    }
}