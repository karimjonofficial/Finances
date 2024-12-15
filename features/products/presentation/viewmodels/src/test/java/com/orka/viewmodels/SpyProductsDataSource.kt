package com.orka.viewmodels

import com.orka.base.Product
import com.orka.base.ProductsDataSource
import com.orka.lib.DESCRIPTION
import com.orka.lib.ID
import com.orka.lib.IMG_SRC
import com.orka.lib.NAME
import com.orka.lib.PRICE

class SpyProductsDataSource : ProductsDataSource {
    var addCalled = false
    var getCalled = false

    override suspend fun get(categoryId: Int): List<Product>? {
        getCalled = true
        return null
    }

    override suspend fun add(name: String, price: Double, description: String, imgSrc: String): Product {
        addCalled = true
        return Product(ID, NAME, PRICE, DESCRIPTION, ID)
    }
}
