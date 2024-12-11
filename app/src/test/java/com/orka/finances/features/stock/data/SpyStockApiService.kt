package com.orka.finances.features.stock.data

import com.orka.finances.features.stock.data.sources.network.StockApiService
import com.orka.finances.features.stock.models.StockItem

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
