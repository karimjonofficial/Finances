package com.orka.stock.fixtures

import com.orka.core.StockDataSource
import com.orka.stock.StockItem

class StockDataSourceImpl : StockDataSource {
    override suspend fun get(categoryId: Int): List<StockItem>? {
        TODO("Not yet implemented")
    }
}