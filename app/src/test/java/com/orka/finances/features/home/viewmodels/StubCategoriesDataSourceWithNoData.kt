package com.orka.finances.features.home.viewmodels

import com.orka.finances.features.home.data.sources.CategoriesDataSource
import com.orka.finances.features.home.models.Category

class StubCategoriesDataSourceWithNoData : CategoriesDataSource {
    override suspend fun get(): List<Category>? {
        return null
    }

    override suspend fun add(name: String, description: String): Category? {
        TODO("Not yet implemented")
    }
}