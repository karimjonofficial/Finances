package com.orka.finances.features.home.data.sources.local

import com.orka.finances.features.home.data.models.CategoryModel
import com.orka.finances.features.home.data.models.toCategory
import com.orka.finances.features.home.data.models.toModel
import com.orka.finances.features.home.data.sources.CategoriesDataSource
import com.orka.finances.features.home.models.Category
import com.orka.finances.lib.errors.data.sources.DataSourceError
import com.orka.finances.lib.errors.data.sources.NullDataSourceError
import com.orka.finances.lib.errors.data.sources.UnknownDataSourceError

class InMemoryCategoriesDataSource(
    private val categories: MutableList<CategoryModel> = emptyList<CategoryModel>().toMutableList()
) : CategoriesDataSource {
    private var lastId: Int = 0

    init {
        if(categories.size > 0) {
            lastId = categories.maxOf { it.id }
        }
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

    override suspend fun get(): Pair<List<Category>, DataSourceError> {
        return Pair(categories.map { toCategory(it) }.toList(), NullDataSourceError)
    }

    override suspend fun add(category: Category): Pair<Category, DataSourceError> {
        try {
            val model = toModel(category.copy(id = lastId++))
            categories.add(model)
            return Pair(toCategory(model), NullDataSourceError)
        } catch (e: Exception) {
            return Pair(category, UnknownDataSourceError())
        }
    }
}