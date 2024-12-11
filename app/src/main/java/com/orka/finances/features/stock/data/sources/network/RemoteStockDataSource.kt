package com.orka.finances.features.stock.data.sources.network

import com.orka.finances.features.stock.data.sources.StockDataSource
import com.orka.finances.features.stock.models.StockItem
import com.orka.finances.lib.data.credentials.Credential

class RemoteStockDataSource(
    private val apiService: StockApiService,
    private val credential: Credential
) : StockDataSource {
    override suspend fun get(categoryId: Int): List<StockItem>? {
        return apiService.get("Bearer ${credential.token}", categoryId)
    }
}