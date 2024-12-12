package com.orka.network

import com.orka.lib.CREDENTIAL
import com.orka.lib.PASSWORD
import com.orka.lib.USERNAME
import com.orka.lib.models.Credential

class MockLoginApiService : LoginApiService {
    override suspend fun getCredential(username: String, password: String): Credential? {
        return if(username == USERNAME && password == PASSWORD) CREDENTIAL else null
    }
}