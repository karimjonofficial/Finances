package com.orka.network

import com.orka.base.CategoriesDataSource
import com.orka.lib.models.Credential
import retrofit2.Retrofit
import retrofit2.create

internal class RemoteCategoriesDataSource(
    private val apiService: CategoriesApiService,
    private val credential: Credential
) : CategoriesDataSource {
    override suspend fun get(): List<com.orka.base.Category>? {
        return apiService.get(getAuthHeader())
    }

    override suspend fun add(name: String, description: String): com.orka.base.Category? {
        return apiService.post(getAuthHeader(), name, description)
    }

    private fun getAuthHeader() = "Bearer ${credential.token}"
}

fun getCategoriesDataSource(retrofit: Retrofit, credential: Credential): CategoriesDataSource {
    val api = retrofit.create<CategoriesApiService>()
    return RemoteCategoriesDataSource(api, credential)
}