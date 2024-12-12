package com.orka.viewmodels

import com.orka.lib.NullResponseBody
import retrofit2.HttpException
import retrofit2.Response

class ThrowingHttpExceptionStubCategoriesDataSource : com.orka.base.CategoriesDataSource {
    override suspend fun get(): List<com.orka.base.Category>? {
        throw HttpException(Response.error<String>(501, NullResponseBody))
    }

    override suspend fun add(name: String, description: String): com.orka.base.Category? {
        throw HttpException(Response.error<String>(501, NullResponseBody))
    }
}
