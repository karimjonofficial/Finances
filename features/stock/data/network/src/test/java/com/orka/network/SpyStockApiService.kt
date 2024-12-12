package com.orka.network

import com.orka.base.StockItem

class SpyStockApiService : StockApiService {
    var authHeader = ""
    var getCalled = false
    var categoryId = 0

    override suspend fun get(authHeader: String, categoryId: Int): List<StockItem>? {
        getCalled = true
        this.categoryId = categoryId
        this.authHeader = authHeader
        return null
    }
}
