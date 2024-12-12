package com.orka.viewmodels

import com.orka.base.Product
import com.orka.base.ProductsDataSource
import com.orka.lib.HTTP_EXCEPTION

class ThrowingHttpProductsDataSource : ProductsDataSource {
    override suspend fun get(): List<Product>? {
        throw HTTP_EXCEPTION
    }
}
