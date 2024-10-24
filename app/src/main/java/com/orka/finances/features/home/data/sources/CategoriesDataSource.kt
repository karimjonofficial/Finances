package com.orka.finances.features.home.data.sources

import com.orka.finances.features.home.models.Category
import com.orka.finances.lib.errors.data.sources.DataSourceError

interface CategoriesDataSource {
    suspend fun get(): Pair<List<Category>, DataSourceError>
    suspend fun add(category: Category): Pair<Category?, DataSourceError>
}