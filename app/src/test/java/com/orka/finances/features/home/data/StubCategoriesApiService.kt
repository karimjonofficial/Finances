package com.orka.finances.features.home.data

import com.orka.finances.features.home.data.sources.network.CategoriesApiService
import com.orka.finances.features.home.models.Category

class StubCategoriesApiService : CategoriesApiService {
    override suspend fun get(authorizationHeader: String): List<Category> {
        throw Exception()
    }

    override suspend fun post(authHeader: String, name: String, description: String): Category? {
        TODO("Not yet implemented")
    }
}