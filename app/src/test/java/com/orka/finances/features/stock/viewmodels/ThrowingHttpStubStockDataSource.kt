package com.orka.finances.features.stock.viewmodels

import com.orka.finances.HTTP_EXCEPTION
import com.orka.finances.features.stock.data.sources.StockDataSource
import com.orka.finances.features.stock.models.StockItem

class ThrowingHttpStubStockDataSource : StockDataSource {
    override suspend fun get(categoryId: Int): List<StockItem>? {
        throw HTTP_EXCEPTION
    }
}
