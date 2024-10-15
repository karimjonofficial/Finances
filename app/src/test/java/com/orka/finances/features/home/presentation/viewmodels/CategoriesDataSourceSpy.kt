package com.orka.finances.features.home.presentation.viewmodels

import com.orka.finances.features.home.data.sources.CategoriesDataSource
import com.orka.finances.features.home.models.Category
import com.orka.finances.lib.errors.data.sources.DataSourceError
import com.orka.finances.lib.errors.data.sources.NullDataSourceError

internal class CategoriesDataSourceSpy : CategoriesDataSource {
    var called = false

    override fun getCategories(): Pair<List<Category>, DataSourceError> {
        return Pair(emptyList(), NullDataSourceError)
    }

    override fun addCategory(name: String, iconName: String, description: String): Pair<Category?, DataSourceError> {
        called = true
        return Pair(null, NullDataSourceError)
    }

    fun isCalled(): Boolean {
        return called
    }
}