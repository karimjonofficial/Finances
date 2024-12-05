package com.orka.finances.features.login.data.sources.network

import com.orka.finances.lib.data.credentials.Credentials

class StubLoginApiService : LoginApiService {
    override suspend fun getCredentials(username: String, password: String): Credentials? {
        throw Exception()
    }
}