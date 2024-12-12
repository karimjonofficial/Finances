package com.orka.viewmodels

import com.orka.lib.UNAUTHORIZED_EXCEPTION

class ThrowingUnauthorizedStubCategoriesDataSource : com.orka.base.CategoriesDataSource {
    override suspend fun get(): List<com.orka.base.Category>? {
        throw UNAUTHORIZED_EXCEPTION
    }

    override suspend fun add(name: String, description: String): com.orka.base.Category? {
        throw UNAUTHORIZED_EXCEPTION
    }
}