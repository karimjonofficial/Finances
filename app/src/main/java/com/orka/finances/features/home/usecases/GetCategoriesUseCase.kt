package com.orka.finances.features.home.usecases

import com.orka.finances.features.home.data.sources.CategoriesDataSource
import com.orka.finances.features.home.entities.Category
import com.orka.finances.lib.errors.data.DataSourceError

class GetCategoriesUseCase(private val dataSource: CategoriesDataSource) {
    fun invoke(): Pair<List<Category>, DataSourceError> {
        return dataSource.getCategories()
    }
}