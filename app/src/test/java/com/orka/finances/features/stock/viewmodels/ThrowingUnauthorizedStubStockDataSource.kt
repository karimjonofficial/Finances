package com.orka.finances.features.stock.viewmodels

import com.orka.finances.UNAUTHORIZED_EXCEPTION
import com.orka.finances.features.stock.data.sources.StockDataSource
import com.orka.finances.features.stock.models.StockItem

class ThrowingUnauthorizedStubStockDataSource : StockDataSource {
    override suspend fun get(categoryId: Int): List<StockItem>? {
        throw UNAUTHORIZED_EXCEPTION
    }
}
