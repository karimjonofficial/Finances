package com.orka.network

import com.orka.base.Product
import com.orka.base.ProductsDataSource
import com.orka.lib.models.Credential
import retrofit2.Retrofit
import retrofit2.create

class RemoteProductsDataSource internal constructor(
    private val apiService: ProductsApiService,
    credential: Credential
) : ProductsDataSource {
    private val authorizationHeader = "Bearer ${credential.token}"

    override suspend fun get(categoryId: Int): List<Product>? {
        return apiService.get(authorizationHeader, categoryId)
    }

    override suspend fun add(name: String, price: Double, description: String, imgSrc: String): Product? {
        return apiService.post(authorizationHeader, name, price, description, imgSrc)
    }

    companion object {
        fun create(retrofit: Retrofit, credential: Credential): ProductsDataSource {
            val apiService = retrofit.create<ProductsApiService>()
            return RemoteProductsDataSource(apiService, credential)
        }
    }
}

class FakeRemoteProductsDataSource internal constructor(
    private val apiService: FakeProductsApiService,
    credential: Credential
) : ProductsDataSource {
    private val authorizationHeader = "Bearer ${credential.token}"

    override suspend fun get(categoryId: Int): List<Product>? {
        return apiService.get(authorizationHeader)
    }

    override suspend fun add(name: String, price: Double, description: String, imgSrc: String): Product? {
        return apiService.post(authorizationHeader, name, price, description, imgSrc)
    }

    companion object {
        fun create(retrofit: Retrofit, credential: Credential): ProductsDataSource {
            val apiService = retrofit.create<FakeProductsApiService>()
            return FakeRemoteProductsDataSource(apiService, credential)
        }
    }
}