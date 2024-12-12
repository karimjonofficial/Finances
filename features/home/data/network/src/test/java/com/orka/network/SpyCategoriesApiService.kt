package com.orka.network

class SpyCategoriesApiService : CategoriesApiService {
    var authorizationHeader = ""
    var postCalled = false
    var getCalled = false

    override suspend fun get(authorizationHeader: String): List<com.orka.base.Category> {
        getCalled = true
        this.authorizationHeader = authorizationHeader
        return emptyList()
    }

    override suspend fun post(authHeader: String, name: String, description: String): com.orka.base.Category? {
        postCalled = true
        return null
    }
}