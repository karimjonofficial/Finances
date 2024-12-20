package com.orka.products.fixtures

import com.orka.core.ProductsDataSource
import com.orka.products.Product

internal class InMemoryProductsDataSource : ProductsDataSource {
    override suspend fun get(): List<Product>? {
        TODO("Not yet implemented")
    }

    override suspend fun get(categoryId: Int): List<Product> {
        val list = emptyList<Product>().toMutableList()
        for(i in 0..20) {
            list.add(Product(i, "Product $i", 1000.0, "Some description", 1))
        }
        return list
    }

    override suspend fun add(name: String, price: Double, description: String, categoryId: Int): Product? {
        TODO("Not yet implemented")
    }
}