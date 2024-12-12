package com.orka.network

import com.orka.base.StockItem

class ThrowingStubStockApiService : StockApiService {
    override suspend fun get(authHeader: String, categoryId: Int): List<StockItem>? {
        throw Exception()
    }
}
