package com.orka.viewmodels

import com.orka.base.StockDataSource
import com.orka.base.StockItem
import com.orka.lib.HTTP_EXCEPTION

class ThrowingHttpStubStockDataSource : StockDataSource {
    override suspend fun get(categoryId: Int): List<StockItem>? {
        throw HTTP_EXCEPTION
    }
}
