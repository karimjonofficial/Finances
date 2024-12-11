package com.orka.finances.features.stock.data

import com.orka.finances.features.stock.data.sources.network.StockApiService
import com.orka.finances.features.stock.models.StockItem

class DummyStockApiService : StockApiService {
    override suspend fun get(authHeader: String, categoryId: Int): List<StockItem>? {
        TODO("Not yet implemented")
    }
}