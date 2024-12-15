package com.orka.viewmodels

import com.orka.base.Product
import com.orka.base.ProductsDataSource
import com.orka.lib.HTTP_EXCEPTION

class ThrowingHttpProductsDataSource : ProductsDataSource {

    override suspend fun get(categoryId: Int): List<Product>? {
        throw HTTP_EXCEPTION
    }

    override suspend fun add(name: String, price: Double, description: String, imgSrc: String): Product? {
        return null
    }
}
