package com.orka.finances.features.stock.viewmodels

import com.orka.finances.features.stock.data.sources.StockDataSource
import com.orka.finances.features.stock.models.StockItem

class DummyStockDataSource : StockDataSource {
    override suspend fun get(categoryId: Int): List<StockItem>? {
        return null
    }
}
