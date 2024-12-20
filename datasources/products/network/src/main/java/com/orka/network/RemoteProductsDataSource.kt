package com.orka.network

import com.orka.core.ProductsDataSource
import com.orka.credentials.Credential
import com.orka.products.Product
import retrofit2.Retrofit
import retrofit2.create

class RemoteProductsDataSource internal constructor(
    private val apiService: ProductsApiService,
    credential: Credential
) : ProductsDataSource {

    private val authorizationHeader = "Bearer ${credential.token}"

    override suspend fun get(): List<Product>? {
        return apiService.get(authorizationHeader)
    }

    override suspend fun get(categoryId: Int): List<Product>? {
        return apiService.get(authorizationHeader, categoryId)
    }

    override suspend fun add(name: String, price: Double, description: String, categoryId: Int): Product? {
        return apiService.post(authorizationHeader, name, price, description, categoryId)
    }

    companion object {
        fun create(retrofit: Retrofit, credential: Credential): ProductsDataSource {
            val apiService = retrofit.create<ProductsApiService>()
            return RemoteProductsDataSource(apiService, credential)
        }
    }
}