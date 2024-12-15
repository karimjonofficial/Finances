package com.orka.viewmodels

import com.orka.base.Product
import com.orka.base.ProductsDataSource

class StubProductsDataSourceWithNoData : ProductsDataSource {

    override suspend fun get(categoryId: Int): List<Product>? {
        return null
    }

    override suspend fun add(name: String, price: Double, description: String, imgSrc: String): Product? {
        return null
    }
}
