package com.orka.finances.features.home.data.sources.local

import com.orka.finances.features.home.data.sources.CategoriesDataSource
import com.orka.finances.features.home.models.Category

class InMemoryCategoriesDataSource(
    private val categories: MutableList<Category> = emptyList<Category>().toMutableList()
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
                Category(
                    id = lastId++,
                    name = it,
                    description = it
                )
            }
        )
    }

    override suspend fun get(token: String): List<Category> {
        return categories
    }

//    override suspend fun add(category: Category): Category {
//        try {
//            val new = category.copy(id = lastId++)
//            categories.add(new)
//            return new
//        } catch (e: Exception) {
//            return category
//        }
//    }
}