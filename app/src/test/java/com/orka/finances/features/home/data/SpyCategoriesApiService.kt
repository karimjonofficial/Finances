package com.orka.finances.features.home.data

import com.orka.finances.features.home.data.sources.network.CategoriesApiService
import com.orka.finances.features.home.models.Category

class SpyCategoriesApiService : CategoriesApiService {
    var token = ""

    override suspend fun get(token: String): List<Category> {
        this.token = token
        return emptyList()
    }

    override suspend fun post(token: String, name: String, description: String): Category? {
        TODO("Not yet implemented")
    }
}