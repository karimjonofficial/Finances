package com.orka.viewmodels

import com.orka.base.StockDataSource
import com.orka.base.StockItem
import com.orka.lib.STOCK_ITEM

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
