package com.orka.product.fixtures

import com.orka.core.AddProductModel
import com.orka.core.ProductsDataSource
import com.orka.core.UpdateProductModel
import com.orka.products.Product

class DummyProductsDataSource : ProductsDataSource {
    override suspend fun getAll(): List<Product>? {
        TODO("Not yet implemented")
    }

    override suspend fun getAll(categoryId: Int): List<Product>? {
        TODO("Not yet implemented")
    }

    override suspend fun get(id: Int): Product {
        TODO("Not yet implemented")
    }

    override suspend fun add(model: AddProductModel): Product? {
        TODO("Not yet implemented")
    }

    override suspend fun update(model: UpdateProductModel): Product? {
        TODO("Not yet implemented")
    }
}
