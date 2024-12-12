package com.orka.viewmodels

import com.orka.lib.Counter

class SpyCategoriesDataSourceForAdd : com.orka.base.CategoriesDataSource {

    val counter = Counter()

    override suspend fun get(): List<com.orka.base.Category> {
        return emptyList()
    }

    override suspend fun add(name: String, description: String): com.orka.base.Category {
        counter.count()
        return com.orka.base.Category(0, name, "description")
    }
}
