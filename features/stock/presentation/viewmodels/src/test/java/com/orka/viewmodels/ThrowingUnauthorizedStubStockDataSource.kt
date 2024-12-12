package com.orka.viewmodels

import com.orka.base.StockDataSource
import com.orka.base.StockItem
import com.orka.lib.UNAUTHORIZED_EXCEPTION

class ThrowingUnauthorizedStubStockDataSource : StockDataSource {
    override suspend fun get(categoryId: Int): List<StockItem>? {
        throw UNAUTHORIZED_EXCEPTION
    }
}
