package com.orka.viewmodels

import com.orka.base.Product
import com.orka.base.ProductsDataSource
import com.orka.lib.PRODUCT

class StubProductsDataSourceWithData : ProductsDataSource {

    override suspend fun get(categoryId: Int): List<Product> {
        return listOf(PRODUCT)
    }

    override suspend fun add(name: String, price: Double, description: String, imgSrc: String): Product? {
        return null
    }
}
