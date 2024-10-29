package com.orka.finances.features.home.data.sources.network

import android.util.Log
import com.orka.finances.features.home.data.sources.CategoriesDataSource
import com.orka.finances.features.home.models.Category

class RemoteCategoriesDataSource(private val apiService: CategoriesApiService) : CategoriesDataSource {
    override suspend fun get(token: String): List<Category>? {
        //TODO Write tests (cause it doesn't return empty list if exception)
        return apiService.get("Bearer $token")
    }
}