package com.orka.network

class StubCategoriesApiService : CategoriesApiService {
    override suspend fun get(authorizationHeader: String): List<com.orka.base.Category> {
        throw Exception()
    }

    override suspend fun post(authorizationHeader: String, name: String, description: String): com.orka.base.Category? {
        TODO("Not yet implemented")
    }
}