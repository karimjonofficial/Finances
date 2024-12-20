package com.orka.core

import com.orka.stock.StockItem

interface StockDataSource {
    suspend fun get(categoryId: Int): List<StockItem>?
}