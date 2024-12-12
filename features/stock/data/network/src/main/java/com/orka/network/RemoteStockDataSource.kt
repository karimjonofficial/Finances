package com.orka.network

import com.orka.base.StockDataSource
import com.orka.base.StockItem
import com.orka.lib.models.Credential
import retrofit2.Retrofit
import retrofit2.create

internal class RemoteStockDataSource(
    private val apiService: StockApiService,
    private val credential: Credential
) : StockDataSource {
    override suspend fun get(categoryId: Int): List<StockItem>? {
        return apiService.get("Bearer ${credential.token}", categoryId)
    }
}

fun getStockDataSource(retrofit: Retrofit, credential: Credential): StockDataSource {
    return RemoteStockDataSource(retrofit.create<StockApiService>(), credential)
}