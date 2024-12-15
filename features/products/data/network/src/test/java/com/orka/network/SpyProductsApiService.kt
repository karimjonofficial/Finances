package com.orka.network

import com.orka.base.Product

class SpyProductsApiService : ProductsApiService {
    var postCalled = false
    var getCalled = false
    var header: String = ""

    override suspend fun get(authorizationHeader: String, categoryId: Int): List<Product>? {
        getCalled = true
        header = authorizationHeader
        return null
    }

    override suspend fun post(
        authorizationHeader: String,
        name: String,
        price: Double,
        description: String,
        imgSrc: String
    ): Product? {
        postCalled = true
        return null
    }
}
