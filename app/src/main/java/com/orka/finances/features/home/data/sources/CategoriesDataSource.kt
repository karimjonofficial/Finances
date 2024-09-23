package com.orka.finances.features.home.data.sources

import com.orka.finances.lib.errors.data.sources.DataSourceError

interface CategoriesDataSource {
    fun getCategories(): Pair<List<String>, DataSourceError>
    fun addCategory(name: String): Pair<String, DataSourceError>
}