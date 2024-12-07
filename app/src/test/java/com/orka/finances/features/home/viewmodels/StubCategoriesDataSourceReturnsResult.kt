package com.orka.finances.features.home.viewmodels

import com.orka.finances.CATEGORY
import com.orka.finances.features.home.data.sources.CategoriesDataSource
import com.orka.finances.features.home.models.Category

class StubCategoriesDataSourceReturnsResult : CategoriesDataSource {
    override suspend fun get(): List<Category> {
        return listOf(CATEGORY)
    }

    override suspend fun add(name: String, description: String): Category {
        return CATEGORY
    }
}
