package com.orka.network

import com.orka.core.CredentialsDataSource
import com.orka.credentials.Credential
import retrofit2.Retrofit
import retrofit2.create

class RemoteCredentialDataSource private constructor(private val apiService: CredentialsApiService) : CredentialsDataSource {
    override suspend fun get(username: String, password: String): Credential? {
        return apiService.get(username, password)
    }

    companion object {
        fun create(retrofit: Retrofit): CredentialsDataSource {
            return RemoteCredentialDataSource(retrofit.create<CredentialsApiService>())
        }
    }
}