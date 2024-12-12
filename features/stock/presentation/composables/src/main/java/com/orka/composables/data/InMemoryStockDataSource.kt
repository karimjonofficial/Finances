package com.orka.composables.data

import com.orka.base.Product
import com.orka.base.StockDataSource
import com.orka.base.StockItem

class InMemoryStockDataSource : StockDataSource {
    override suspend fun get(categoryId: Int): List<StockItem> {
        val items = emptyList<StockItem>().toMutableList()
        for (i in 0..20) {
            items.add(StockItem(i, Product(i, "Product $i", 10000.0, ""), 1, 2000))
        }
        return items
    }
}