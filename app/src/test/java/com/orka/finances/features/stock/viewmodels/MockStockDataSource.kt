package com.orka.finances.features.stock.viewmodels

import com.orka.finances.STOCK_ITEM
import com.orka.finances.features.stock.data.sources.StockDataSource
import com.orka.finances.features.stock.models.StockItem

class MockStockDataSource : StockDataSource {
    private var calls = 0
    override suspend fun get(categoryId: Int): List<StockItem>? {
        if(calls == 0) {
            calls++
            return listOf(STOCK_ITEM)
        } else {
            return null
        }
    }
}
