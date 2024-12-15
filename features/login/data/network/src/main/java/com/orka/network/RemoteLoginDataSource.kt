package com.orka.network

import com.orka.base.LoginDataSource
import com.orka.lib.models.Credential
import retrofit2.Retrofit
import retrofit2.create

class RemoteLoginDataSource private constructor(private val apiService: LoginApiService) : LoginDataSource {
    override suspend fun getCredential(username: String, password: String): Credential? {
        return apiService.getCredential(username, password)
    }

    companion object {
        fun create(retrofit: Retrofit): LoginDataSource {
            return RemoteLoginDataSource(retrofit.create<LoginApiService>())
        }
    }
}
