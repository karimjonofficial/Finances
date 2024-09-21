package com.orka.finances.features.home.usecases

import com.orka.finances.features.home.data.sources.CategoriesDataSource
import com.orka.finances.features.home.entities.Category
import com.orka.finances.lib.errors.data.DataSourceError

class AddCategoryUseCase(
    private val dataSource: CategoriesDataSource
) {
    fun invoke(name: String): Pair<Category, DataSourceError> {
        return dataSource.addCategory(name)
    }
}