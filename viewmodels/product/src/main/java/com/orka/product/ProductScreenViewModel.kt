package com.orka.product

import com.orka.core.HttpService
import com.orka.core.ProductsDataSource
import com.orka.core.SingleStateFsm
import com.orka.core.UpdateProductModel
import com.orka.products.Product

class ProductScreenViewModel(
    private val productId: Int,
    private val dataSource: ProductsDataSource,
    private val httpService: HttpService
) : SingleStateFsm(ProductScreenStates.Initial(productId)) {

    suspend fun getProduct(id: Int): Product {
        return httpService.invoke {
            return@invoke dataSource.get(id)
        } ?: throw Exception()
    }

    suspend fun update(name: String, price: Double, description: String, categoryId: Int): Product? {
        return dataSource.update(UpdateProductModel(productId, name, price, description, categoryId))
    }
}