package com.orka.finances.features.login.data.sources.network

import com.orka.finances.lib.data.credentials.Credential


class StubLoginApiService : LoginApiService {
    override suspend fun getCredential(username: String, password: String): Credential? {
        throw Exception()
    }
}