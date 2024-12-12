package com.orka.network

import com.orka.base.StockItem

class DummyStockApiService : StockApiService {
    override suspend fun get(authHeader: String, categoryId: Int): List<StockItem>? {
        TODO("Not yet implemented")
    }
}