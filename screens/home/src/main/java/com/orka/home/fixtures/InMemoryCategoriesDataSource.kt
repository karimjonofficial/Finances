package com.orka.home.fixtures

import com.orka.categories.Category
import com.orka.core.CategoriesDataSource

internal class InMemoryCategoriesDataSource : CategoriesDataSource {
    private val categories = MutableList(
        size = 20,
        init = { Category(it, "Category $it", "Description $it") }
    )

    private var lastId = 20

    override suspend fun get(): List<Category> {
        return categories
    }

    override suspend fun add(name: String, description: String): Category {
        val category = Category(lastId++, name, description)
        categories.add(category)
        return category
    }
}
