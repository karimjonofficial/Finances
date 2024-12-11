package com.orka.finances.features.home.viewmodels

import com.orka.finances.NullResponseBody
import com.orka.finances.features.home.data.sources.CategoriesDataSource
import com.orka.finances.features.home.models.Category
import retrofit2.HttpException
import retrofit2.Response

class ThrowingHttpExceptionStubCategoriesDataSource : CategoriesDataSource {
    override suspend fun get(): List<Category>? {
        throw HttpException(Response.error<String>(501, NullResponseBody))
    }

    override suspend fun add(name: String, description: String): Category? {
        throw HttpException(Response.error<String>(501, NullResponseBody))
    }
}
