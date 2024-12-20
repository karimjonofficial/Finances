package com.orka.stock.fixtures

import com.orka.core.ProductsDataSource
import com.orka.products.Product

class ProductDataSourceImpl : ProductsDataSource {
    override suspend fun get(): List<Product>? {
        TODO("Not yet implemented")
    }

    override suspend fun get(categoryId: Int): List<Product>? {
        TODO("Not yet implemented")
    }

    override suspend fun add(
        name: String,
        price: Double,
        description: String,
        categoryId: Int
    ): Product? {
        TODO("Not yet implemented")
    }

}