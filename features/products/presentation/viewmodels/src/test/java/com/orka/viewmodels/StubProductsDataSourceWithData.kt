package com.orka.viewmodels

import com.orka.base.Product
import com.orka.base.ProductsDataSource

class StubProductsDataSourceWithData : ProductsDataSource {
    override suspend fun get(): List<Product> {
        return listOf(Product(0))
    }
}
