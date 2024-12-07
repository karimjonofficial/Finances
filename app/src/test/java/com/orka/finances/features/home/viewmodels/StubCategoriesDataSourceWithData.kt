package com.orka.finances.features.home.viewmodels

import com.orka.finances.DESCRIPTION
import com.orka.finances.ID
import com.orka.finances.NAME
import com.orka.finances.features.home.data.sources.CategoriesDataSource
import com.orka.finances.features.home.models.Category

class StubCategoriesDataSourceWithData :
    CategoriesDataSource {
    override suspend fun get(token: String): List<Category> {
        return listOf(Category(ID, NAME, DESCRIPTION))
    }

    override suspend fun add(token: String, name: String, description: String): Category? {
        TODO("Not yet implemented")
    }
}
