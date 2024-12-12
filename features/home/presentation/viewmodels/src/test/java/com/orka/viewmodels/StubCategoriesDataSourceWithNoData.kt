package com.orka.viewmodels

class StubCategoriesDataSourceWithNoData : com.orka.base.CategoriesDataSource {
    override suspend fun get(): List<com.orka.base.Category>? {
        return null
    }

    override suspend fun add(name: String, description: String): com.orka.base.Category? {
        TODO("Not yet implemented")
    }
}