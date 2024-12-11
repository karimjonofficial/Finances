package com.orka.finances.features.stock.viewmodels

import com.orka.finances.STOCK_ITEM
import com.orka.finances.features.stock.data.sources.StockDataSource
import com.orka.finances.features.stock.models.StockItem

class StubStockDataSource : StockDataSource {
    override suspend fun get(categoryId: Int): List<StockItem> {
        return listOf(STOCK_ITEM)
    }
}