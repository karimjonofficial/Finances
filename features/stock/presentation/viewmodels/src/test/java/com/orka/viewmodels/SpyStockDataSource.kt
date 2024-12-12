package com.orka.viewmodels

import com.orka.base.StockDataSource
import com.orka.base.StockItem

class SpyStockDataSource : StockDataSource {
    var getCalled: Boolean = false

    override suspend fun get(categoryId: Int): List<StockItem>? {
        getCalled = true
        return null
    }
}
