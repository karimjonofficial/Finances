package com.orka.finances.features.home.viewmodels

import com.orka.finances.features.home.data.sources.CategoriesDataSource
import com.orka.finances.features.home.models.Category

class DummyCategoriesDataSource : CategoriesDataSource {
    override suspend fun get(token: String): List<Category>? {
        return null
    }

    override suspend fun add(token: String, name: String, description: String): Category? {
        return null
    }

}
