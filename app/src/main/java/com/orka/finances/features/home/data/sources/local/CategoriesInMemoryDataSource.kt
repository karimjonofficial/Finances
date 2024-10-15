package com.orka.finances.features.home.data.sources.local

import com.orka.finances.features.home.data.sources.CategoriesDataSource
import com.orka.finances.features.home.models.Category
import com.orka.finances.lib.errors.data.sources.DataSourceError
import com.orka.finances.lib.errors.data.sources.NullDataSourceError

class CategoriesInMemoryDataSource(
    private val categories: MutableList<Category> = emptyList<Category>().toMutableList()
) : CategoriesDataSource {
    private var lastId: Int = 0

    init {
        if(categories.size > 0) {
            lastId = categories.maxOf { it.id }
        }
    }

    override fun getCategories(): Pair<List<Category>, DataSourceError> {
        return Pair(categories.toList(), NullDataSourceError)
    }

    override fun addCategory(name: String, iconName: String, description: String): Pair<Category, DataSourceError> {
        val category = Category(lastId++, name, iconName, description)
        categories.add(category)
        return Pair(category, NullDataSourceError)
    }

    fun loadInitialData() {
        categories.addAll(
            categoriesList.map {
                Category(id = lastId++, name = it, iconName =  "chair", description = it)
            }
        )
    }
}