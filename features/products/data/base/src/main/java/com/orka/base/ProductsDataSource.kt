package com.orka.base

interface ProductsDataSource {
    suspend fun get(): List<Product>?
}