package com.orka.finances.features.login.data.sources.network

import com.orka.finances.CREDENTIAL
import com.orka.finances.PASSWORD
import com.orka.finances.USERNAME
import com.orka.finances.lib.data.credentials.Credential

class MockLoginApiService : LoginApiService {
    override suspend fun getCredential(username: String, password: String): Credential? {
        return if(username == USERNAME && password == PASSWORD) CREDENTIAL else null
    }
}