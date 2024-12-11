package com.orka.finances.features.stock.data

import com.orka.finances.features.stock.data.sources.network.StockApiService
import com.orka.finances.features.stock.models.StockItem

class ThrowingStubStockApiService : StockApiService {
    override suspend fun get(authHeader: String, categoryId: Int): List<StockItem>? {
        throw Exception()
    }
}
