package com.orka.finances.features.stock.data.sources

import com.orka.finances.features.stock.models.StockItem

interface StockDataSource {
    suspend fun get(categoryId: Int): List<StockItem>?
}