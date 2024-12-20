package com.orka.network

import com.orka.core.StockDataSource
import com.orka.credentials.Credential
import com.orka.stock.StockItem
import retrofit2.Retrofit
import retrofit2.create

class RemoteStockDataSource private constructor(
    private val apiService: StockApiService,
    private val credential: Credential
) : StockDataSource {

    override suspend fun get(categoryId: Int): List<StockItem>? {
        return apiService.get("Bearer ${credential.token}", categoryId)
    }

    companion object {
        fun create(retrofit: Retrofit, credential: Credential): StockDataSource {
            return RemoteStockDataSource(retrofit.create<StockApiService>(), credential)
        }
    }
}