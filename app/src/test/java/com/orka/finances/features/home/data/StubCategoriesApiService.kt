package com.orka.finances.features.home.data

import com.orka.finances.features.home.data.sources.network.CategoriesApiService
import com.orka.finances.features.home.models.Category

class StubCategoriesApiService : CategoriesApiService {
    override suspend fun get(token: String): List<Category> {
        throw Exception()
    }
}