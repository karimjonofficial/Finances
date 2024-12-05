package com.orka.finances.features.home.viewmodels

import com.orka.finances.features.home.data.sources.CategoriesDataSource
import com.orka.finances.features.home.models.Category

class SpyCategoriesDataSource : CategoriesDataSource {
    var called = false

    override suspend fun get(token: String): List<Category> {
        called = true
        return emptyList()
    }
}