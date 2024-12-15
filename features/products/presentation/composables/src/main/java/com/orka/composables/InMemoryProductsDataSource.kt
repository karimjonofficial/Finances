package com.orka.composables

import com.orka.base.Product
import com.orka.base.ProductsDataSource

internal class InMemoryProductsDataSource : ProductsDataSource {
    override suspend fun get(categoryId: Int): List<Product> {
        val list = emptyList<Product>().toMutableList()
        for(i in 0..20) {
            list.add(Product(i, "Product $i", 1000.0, "Some description", 1))
        }
        return list
    }

    override suspend fun add(name: String, price: Double, description: String, imgSrc: String): Product? {
        TODO("Not yet implemented")
    }
}
