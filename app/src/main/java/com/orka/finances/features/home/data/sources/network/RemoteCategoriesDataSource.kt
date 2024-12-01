package com.orka.finances.features.home.data.sources.network

import com.orka.finances.features.home.data.sources.CategoriesDataSource
import com.orka.finances.features.home.models.Category

class RemoteCategoriesDataSource(private val apiService: CategoriesApiService) : CategoriesDataSource {
    override suspend fun get(token: String): List<Category>? {
        return apiService.get("Bearer $token")
    }
}