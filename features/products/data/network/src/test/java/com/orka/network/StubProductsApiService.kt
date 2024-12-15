package com.orka.network

import com.orka.base.Product

class StubProductsApiService : ProductsApiService {
    override suspend fun get(authorizationHeader: String, categoryId: Int): List<Product>? {
        return null
    }

    override suspend fun post(
        authorizationHeader: String,
        name: String,
        price: Double,
        description: String,
        imgSrc: String
    ): Product? {
        return null
    }
}
