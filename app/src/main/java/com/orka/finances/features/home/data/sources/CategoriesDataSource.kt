package com.orka.finances.features.home.data.sources

import com.orka.finances.features.home.data.models.CategoryModel
import com.orka.finances.lib.errors.data.sources.DataSourceError

interface CategoriesDataSource {
    suspend fun get(): Pair<List<CategoryModel>, DataSourceError>
    suspend fun add(name: String, iconName: String = "", description: String = ""): Pair<CategoryModel?, DataSourceError>
}