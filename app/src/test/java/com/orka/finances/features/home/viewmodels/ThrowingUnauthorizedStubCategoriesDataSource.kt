package com.orka.finances.features.home.viewmodels

import com.orka.finances.UNAUTHORIZED_STATUS_CODE
import com.orka.finances.features.home.data.sources.CategoriesDataSource
import com.orka.finances.features.home.models.Category
import retrofit2.HttpException
import retrofit2.Response

class ThrowingUnauthorizedStubCategoriesDataSource : CategoriesDataSource {
    override suspend fun get(token: String): List<Category>? {
        throw HttpException(Response.error<String>(UNAUTHORIZED_STATUS_CODE, NullResponseBody))
    }

    override suspend fun add(token: String, name: String, description: String): Category? {
        throw HttpException(Response.error<String>(UNAUTHORIZED_STATUS_CODE, NullResponseBody))
    }
}