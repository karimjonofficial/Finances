package com.orka.finances.features.home.data.sources.local

import androidx.annotation.DrawableRes
import com.orka.finances.features.home.data.sources.CategoriesDataSource
import com.orka.finances.features.home.models.Category
import com.orka.finances.R
import com.orka.finances.lib.errors.data.sources.DataSourceError
import com.orka.finances.lib.errors.data.sources.NullDataSourceError

class CategoriesInMemoryDataSource : CategoriesDataSource {
    private val categories: MutableList<Category> = emptyList<Category>().toMutableList()

    override fun getCategories(): Pair<List<Category>, DataSourceError> {
        return Pair(categories.toList(), NullDataSourceError)
    }

    override fun addCategory(name: String, @DrawableRes imageRes: Int, description: String): Pair<Category, DataSourceError> {
        val category = Category(0, name, imageRes, description)
        categories.add(category)
        return Pair(category, NullDataSourceError)
    }

    fun loadInitialData() {
        var id = 0

        categories.addAll(
            categoriesList.map {
                Category(id++, it, R.drawable.chair, it)
            }
        )
    }
}