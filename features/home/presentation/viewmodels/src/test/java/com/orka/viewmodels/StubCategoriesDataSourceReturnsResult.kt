package com.orka.viewmodels

import com.orka.lib.CATEGORY

class StubCategoriesDataSourceReturnsResult : com.orka.base.CategoriesDataSource {
    override suspend fun get(): List<com.orka.base.Category> {
        return listOf(CATEGORY)
    }

    override suspend fun add(name: String, description: String): com.orka.base.Category {
        return CATEGORY
    }
}
