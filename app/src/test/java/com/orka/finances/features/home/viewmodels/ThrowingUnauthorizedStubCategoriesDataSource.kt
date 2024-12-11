package com.orka.finances.features.home.viewmodels

import com.orka.finances.UNAUTHORIZED_EXCEPTION
import com.orka.finances.features.home.data.sources.CategoriesDataSource
import com.orka.finances.features.home.models.Category

class ThrowingUnauthorizedStubCategoriesDataSource : CategoriesDataSource {
    override suspend fun get(): List<Category>? {
        throw UNAUTHORIZED_EXCEPTION
    }

    override suspend fun add(name: String, description: String): Category? {
        throw UNAUTHORIZED_EXCEPTION
    }
}