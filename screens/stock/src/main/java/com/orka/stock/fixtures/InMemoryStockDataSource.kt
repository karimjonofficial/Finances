package com.orka.stock.fixtures

import com.orka.core.StockDataSource
import com.orka.products.Product
import com.orka.stock.StockItem

internal class InMemoryStockDataSource : StockDataSource {
    override suspend fun get(categoryId: Int): List<StockItem> {
        val items = emptyList<StockItem>().toMutableList()
        for (i in 0..20) {
            items.add(StockItem(i, Product(i, "Product $i", 10000.0, "", categoryId = 0), 2000))
        }
        return items
    }
}