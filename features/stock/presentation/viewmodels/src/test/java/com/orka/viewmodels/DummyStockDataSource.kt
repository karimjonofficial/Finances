package com.orka.viewmodels

import com.orka.base.StockDataSource
import com.orka.base.StockItem

class DummyStockDataSource : StockDataSource {
    override suspend fun get(categoryId: Int): List<StockItem>? {
        return null
    }
}
