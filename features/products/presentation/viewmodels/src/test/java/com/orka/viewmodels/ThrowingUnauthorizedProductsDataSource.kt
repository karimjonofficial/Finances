package com.orka.viewmodels

import com.orka.base.Product
import com.orka.base.ProductsDataSource
import com.orka.lib.UNAUTHORIZED_EXCEPTION

class ThrowingUnauthorizedProductsDataSource : ProductsDataSource {

    override suspend fun get(categoryId: Int): List<Product>? {
        throw UNAUTHORIZED_EXCEPTION
    }

    override suspend fun add(name: String, price: Double, description: String, imgSrc: String): Product? {
        return null
    }
}
