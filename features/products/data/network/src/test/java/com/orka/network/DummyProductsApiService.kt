package com.orka.network

import com.orka.base.Product

class DummyProductsApiService : ProductsApiService {

    override suspend fun get(authorizationHeader: String, categoryId: Int): List<Product>? {
        TODO("Not yet implemented")
    }

    override suspend fun post(
        authorizationHeader: String,
        name: String,
        price: Double,
        description: String,
        imgSrc: String
    ): Product? {
        TODO("Not yet implemented")
    }

}
