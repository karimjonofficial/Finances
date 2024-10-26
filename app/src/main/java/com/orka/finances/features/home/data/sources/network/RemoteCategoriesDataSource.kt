package com.orka.finances.features.home.data.sources.network

import android.util.Log
import com.orka.finances.features.home.data.sources.CategoriesDataSource
import com.orka.finances.features.home.models.Category

class RemoteCategoriesDataSource(private val apiService: CategoriesApiService) : CategoriesDataSource {
    override suspend fun get(token: String): List<Category>? {
        return try {
            apiService.get("Bearer $token")
        } catch(e: Exception) {
            Log.d("FinancesApp.HTTP", e.message.toString())
            emptyList()
        }
    }
}