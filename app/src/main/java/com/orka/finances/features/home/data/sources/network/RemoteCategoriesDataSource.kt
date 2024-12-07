package com.orka.finances.features.home.data.sources.network

import com.orka.finances.features.home.data.sources.CategoriesDataSource
import com.orka.finances.features.home.models.Category
import com.orka.finances.lib.data.credentials.Credential

class RemoteCategoriesDataSource(
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
}