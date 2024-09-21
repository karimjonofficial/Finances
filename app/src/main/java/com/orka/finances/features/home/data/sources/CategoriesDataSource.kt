package com.orka.finances.features.home.data.sources

import com.orka.finances.features.home.entities.Category
import com.orka.finances.lib.errors.data.DataSourceError

interface CategoriesDataSource {
    fun getCategories(): Pair<List<Category>, DataSourceError>
    fun addCategory(name: String): Pair<Category, DataSourceError>
}