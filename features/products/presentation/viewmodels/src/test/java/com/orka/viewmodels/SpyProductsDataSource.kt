package com.orka.viewmodels

import com.orka.base.Product
import com.orka.base.ProductsDataSource

class SpyProductsDataSource : ProductsDataSource {
    var getCalled = false

    override suspend fun get(): List<Product>? {
        getCalled = true
        return null
    }
}
