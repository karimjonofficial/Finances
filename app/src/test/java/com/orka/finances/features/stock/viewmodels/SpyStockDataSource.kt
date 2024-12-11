package com.orka.finances.features.stock.viewmodels

import com.orka.finances.features.stock.data.sources.StockDataSource
import com.orka.finances.features.stock.models.StockItem

class SpyStockDataSource : StockDataSource {
    var getCalled: Boolean = false

    override suspend fun get(categoryId: Int): List<StockItem>? {
        getCalled = true
        return null
    }
}
