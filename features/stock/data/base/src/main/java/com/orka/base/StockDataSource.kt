package com.orka.base

interface StockDataSource {
    suspend fun get(categoryId: Int): List<StockItem>?
}