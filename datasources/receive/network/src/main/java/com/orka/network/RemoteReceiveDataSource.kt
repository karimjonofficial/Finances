package com.orka.network

import com.orka.core.ReceiveDataSource
import com.orka.core.models.PostRequestModel
import com.orka.credentials.Credential
import com.orka.receive.Receive
import retrofit2.Retrofit
import retrofit2.create

class RemoteReceiveDataSource internal constructor(
    private val apiService: ReceiveApiService,
    credential: Credential
) : ReceiveDataSource {

    private val authorizationHeader = "Bearer ${credential.token}"

    override suspend fun get(): List<Receive>? {
        return apiService.get(authorizationHeader)
    }

    override suspend fun add(body: PostRequestModel): Receive? {
        apiService.post(authorizationHeader, body)
        return null
    }

    companion object {
        fun create(retrofit: Retrofit, credential: Credential): ReceiveDataSource {
            return RemoteReceiveDataSource(retrofit.create<ReceiveApiService>(), credential)
        }
    }
}