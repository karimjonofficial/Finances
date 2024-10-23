package com.orka.finances.features.home.data.sources.local

import com.orka.finances.features.home.data.models.CategoryModel
import com.orka.finances.features.home.data.sources.CategoriesDataSource
import com.orka.finances.lib.errors.data.sources.DataSourceError
import com.orka.finances.lib.errors.data.sources.NullDataSourceError

class CategoriesInMemoryDataSource(
    private val categories: MutableList<CategoryModel> = emptyList<CategoryModel>().toMutableList()
) : CategoriesDataSource {
    private var lastId: Int = 0

    init {
        if(categories.size > 0) {
            lastId = categories.maxOf { it.id }
        }
    }

    override suspend fun get(): Pair<List<CategoryModel>, DataSourceError> {
        return Pair(categories.toList(), NullDataSourceError)
    }

    override suspend fun add(name: String, iconName: String, description: String): Pair<CategoryModel, DataSourceError> {
        val category = CategoryModel(lastId++, name, iconName, description)
        categories.add(category)
        return Pair(category, NullDataSourceError)
    }

    fun loadInitialData() {
        categories.addAll(
            categoriesList.map {
                CategoryModel(
                    id = lastId++,
                    name = it,
                    iconName = "chair",
                    description = it
                )
            }
        )
    }
}