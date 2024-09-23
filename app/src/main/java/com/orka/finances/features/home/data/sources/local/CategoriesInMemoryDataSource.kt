package com.orka.finances.features.home.data.sources.local

import com.orka.finances.features.home.data.sources.CategoriesDataSource
import com.orka.finances.lib.errors.data.sources.DataSourceError
import com.orka.finances.lib.errors.data.sources.NullDataSourceError

class CategoriesInMemoryDataSource : CategoriesDataSource {
    private val categories: MutableList<String> = emptyList<String>().toMutableList()

    override fun getCategories(): Pair<List<String>, DataSourceError> {
        return Pair(categories.toList(), NullDataSourceError)
    }

    override fun addCategory(name: String): Pair<String, DataSourceError> {
        categories.add(name)
        return Pair(name, NullDataSourceError)
    }

    fun loadInitialData() {
        categories.addAll(products)
    }
}