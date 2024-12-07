package com.orka.finances.features.products.presentation.viewmodels

import com.orka.finances.features.products.data.sources.ProductsDataSource
import com.orka.finances.features.products.models.Product

class SpyProductsDataSource : ProductsDataSource {
    var getCalled: Boolean = false

    override fun get(categoryId: Int): List<Product>? {
        getCalled = true
        return null
    }
}
