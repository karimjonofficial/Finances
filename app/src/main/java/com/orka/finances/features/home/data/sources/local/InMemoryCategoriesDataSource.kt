package com.orka.finances.features.home.data.sources.local

import com.orka.finances.features.home.data.sources.CategoriesDataSource
import com.orka.finances.features.home.models.Category

class InMemoryCategoriesDataSource : CategoriesDataSource {
    private val categories = MutableList(
        size = 20,
        init = { Category(it, "Category $it", "Description $it") }
    )

    private var lastId = 20

    override suspend fun get(token: String): List<Category> {
        return categories
    }

    override suspend fun add(token: String, name: String, description: String): Category {
        val category = Category(lastId++, name, description)
        categories.add(category)
        return category
    }

}
