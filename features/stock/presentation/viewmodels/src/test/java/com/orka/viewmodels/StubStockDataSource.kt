package com.orka.viewmodels

import com.orka.base.StockDataSource
import com.orka.base.StockItem
import com.orka.lib.STOCK_ITEM

class StubStockDataSource : StockDataSource {
    override suspend fun get(categoryId: Int): List<StockItem> {
        return listOf(STOCK_ITEM)
    }
}