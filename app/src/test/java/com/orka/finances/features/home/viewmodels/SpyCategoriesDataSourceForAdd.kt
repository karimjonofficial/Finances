package com.orka.finances.features.home.viewmodels

import com.orka.finances.Counter
import com.orka.finances.features.home.data.sources.CategoriesDataSource
import com.orka.finances.features.home.models.Category

class SpyCategoriesDataSourceForAdd : CategoriesDataSource {

    val counter = Counter()

    override suspend fun get(token: String): List<Category> {
        return emptyList()
    }

    override suspend fun add(token: String, name: String, description: String): Category {
        counter.count()
        return Category(0, name, "description")
    }
}
