package com.orka.viewmodels

import com.orka.base.Product
import com.orka.base.ProductsDataSource

class DummyProductsDataSource : ProductsDataSource {
    override suspend fun get(): List<Product>? {
        return null
    }
}
