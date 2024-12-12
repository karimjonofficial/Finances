package com.orka.network

import com.orka.lib.models.Credential


class StubLoginApiService : LoginApiService {
    override suspend fun getCredential(username: String, password: String): Credential? {
        throw Exception()
    }
}