package com.orka.finances.features.home.data.sources.local

import com.orka.finances.features.home.data.models.CategoryModel
import com.orka.finances.features.home.data.models.toCategory
import com.orka.finances.features.home.data.models.toModel
import com.orka.finances.features.home.data.sources.CategoriesDataSource
import com.orka.finances.features.home.models.Category

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

    override suspend fun get(): List<Category> {
        return categories.map { toCategory(it) }.toList()
    }

    override suspend fun add(category: Category): Category {
        try {
            val model = toModel(category.copy(id = lastId++))
            categories.add(model)
            return toCategory(model)
        } catch (e: Exception) {
            return category
        }
    }
}