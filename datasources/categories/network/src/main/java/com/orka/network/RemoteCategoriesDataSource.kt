package com.orka.network

import com.orka.categories.Category
import com.orka.core.CategoriesDataSource
import com.orka.credentials.Credential
import retrofit2.Retrofit
import retrofit2.create

class RemoteCategoriesDataSource private constructor(
    private val apiService: CategoriesApiService,
    private val credential: Credential
) : CategoriesDataSource {
    override suspend fun get(): List<Category>? {
        return apiService.get(getAuthHeader())
    }

    override suspend fun add(name: String, description: String): Category? {
        return apiService.post(getAuthHeader(), name, description)
    }

    private fun getAuthHeader() = "Bearer ${credential.token}"

    companion object {
        fun create(retrofit: Retrofit, credential: Credential): CategoriesDataSource {
            val api = retrofit.create<CategoriesApiService>()
            return RemoteCategoriesDataSource(api, credential)
        }
    }
}