package com.orka.finances.features.products.data.sources.local

import com.orka.finances.features.products.data.sources.ProductsDataSource
import com.orka.finances.features.products.models.Product

class InMemoryProductsDataSource : ProductsDataSource {
    override fun get(categoryId: Int): List<Product> {
        return emptyList()
    }
}