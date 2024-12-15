package com.orka.network

import com.orka.base.Product
import com.orka.lib.UNAUTHORIZED_EXCEPTION

class ThrowingStubProductsApiService : ProductsApiService {

    override suspend fun get(authorizationHeader: String, categoryId: Int): List<Product>? {
        throw UNAUTHORIZED_EXCEPTION
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
