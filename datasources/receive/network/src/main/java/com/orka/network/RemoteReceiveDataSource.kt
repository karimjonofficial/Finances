package com.orka.network

import com.orka.core.ReceiveDataSource
import com.orka.credentials.Credential
import com.orka.receive.Receive
import com.orka.receive.ReceiveItem
import retrofit2.Retrofit
import retrofit2.create

class RemoteReceiveDataSource internal constructor(
    private val apiService: ReceiveApiService,
    credential: Credential
) : ReceiveDataSource {

    private val authorizationHeader = "Bearer ${credential.token}"

    override suspend fun get(): List<Receive>? {
        return apiService.get(authorizationHeader)?.map {
            Receive(
                id = it.id,
                items = it.items.map { item ->
                    ReceiveItem(item.product.id, item.amount)
                },
                datetime = it.dateTime,
                price = it.price,
                comment = it.comment,
                userId = it.userId,
            )
        }
    }

    override suspend fun add(items: List<ReceiveItem>, price: Double, comment: String): Receive? {
        apiService.post(authorizationHeader, RequestModel(items, price.toString(), comment))
        return null
    }

    companion object {
        fun create(retrofit: Retrofit, credential: Credential): ReceiveDataSource {
            return RemoteReceiveDataSource(retrofit.create<ReceiveApiService>(), credential)
        }
    }
}