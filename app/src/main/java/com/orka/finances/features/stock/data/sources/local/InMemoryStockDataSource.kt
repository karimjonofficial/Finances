package com.orka.finances.features.stock.data.sources.local

import com.orka.finances.features.stock.data.sources.StockDataSource
import com.orka.finances.features.stock.models.Product
import com.orka.finances.features.stock.models.StockItem

class InMemoryStockDataSource : StockDataSource {
    override suspend fun get(categoryId: Int): List<StockItem> {
        val items = emptyList<StockItem>().toMutableList()
        for (i in 0..20) {
            items.add(StockItem(i, Product(i, "Product $i", 10000.0, ""), 1, 2000))
        }
        return items
    }
}