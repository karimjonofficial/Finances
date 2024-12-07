package com.orka.finances.features.products.presentation.viewmodels

import com.orka.finances.features.products.data.sources.ProductsDataSource
import com.orka.finances.features.products.models.Product

class DummyProductsDataSource : ProductsDataSource {
    override fun get(categoryId: Int): List<Product>? {
        return null
    }
}
