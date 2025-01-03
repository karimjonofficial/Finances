package com.orka.network

import com.orka.core.AddProductModel
import com.orka.core.ProductsDataSource
import com.orka.core.UpdateProductModel
import com.orka.credentials.Credential
import com.orka.products.Product
import retrofit2.Retrofit
import retrofit2.create

class RemoteProductsDataSource internal constructor(
    private val apiService: ProductsApiService,
    credential: Credential
) : ProductsDataSource {

    private val authorizationHeader = "Bearer ${credential.token}"

    override suspend fun getAll(): List<Product>? {
        return apiService.getAll(authorizationHeader)
    }

    override suspend fun getAll(categoryId: Int): List<Product>? {
        return apiService.getAll(authorizationHeader, categoryId)
    }

    override suspend fun get(id: Int): Product {
        return apiService.get(authorizationHeader, id)
    }

    override suspend fun add(model: AddProductModel): Product? {
        return apiService.post(authorizationHeader, model.name, model.price, model.description, model.categoryId)
    }

    override suspend fun update(model: UpdateProductModel): Product? {
        return apiService.patch(authorizationHeader, model.id, model.name, model.price, model.description, model.categoryId)
    }

    companion object {
        fun create(retrofit: Retrofit, credential: Credential): ProductsDataSource {
            val apiService = retrofit.create<ProductsApiService>()
            return RemoteProductsDataSource(apiService, credential)
        }
    }
}