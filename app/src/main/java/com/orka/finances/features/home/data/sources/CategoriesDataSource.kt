package com.orka.finances.features.home.data.sources

import com.orka.finances.features.home.models.Category
import com.orka.finances.lib.errors.data.sources.DataSourceError

interface CategoriesDataSource {
    fun getCategories(): Pair<List<Category>, DataSourceError>
    fun addCategory(name: String, imageRes: Int, description: String): Pair<Category, DataSourceError>
}