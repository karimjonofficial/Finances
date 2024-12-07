package com.orka.finances.features.home.data

import com.orka.finances.features.home.data.sources.network.CategoriesApiService
import com.orka.finances.features.home.models.Category

class SpyCategoriesApiService : CategoriesApiService {
    var authorizationHeader = ""
    var postCalled = false
    var getCalled = false

    override suspend fun get(authorizationHeader: String): List<Category> {
        getCalled = true
        this.authorizationHeader = authorizationHeader
        return emptyList()
    }

    override suspend fun post(authHeader: String, name: String, description: String): Category? {
        postCalled = true
        return null
    }
}