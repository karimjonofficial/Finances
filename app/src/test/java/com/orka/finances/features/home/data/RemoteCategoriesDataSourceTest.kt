package com.orka.finances.features.home.data

import com.orka.finances.features.home.data.sources.network.CategoriesApiService
import com.orka.finances.features.home.data.sources.network.RemoteCategoriesDataSource
import com.orka.finances.features.home.models.Category
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

private const val TOKEN = "token"

class RemoteCategoriesDataSourceTest {
    @Test
    fun passesTokenToApiService() = runBlocking {
        val apiService = SpyCategoriesApiService()
        val dataSource = RemoteCategoriesDataSource(apiService)
        dataSource.get(TOKEN)
        assertEquals(TOKEN, apiService.token)
    }

    @Test
    fun returnEmptyListIfRequestFailed() = runBlocking {
        val apiService = DummyCategoriesApiService()
        val dataSource = RemoteCategoriesDataSource(apiService)
        assertEquals(emptyList<Category>(), dataSource.get(TOKEN))
    }
}

class DummyCategoriesApiService : CategoriesApiService {
    override suspend fun get(token: String): List<Category> {
        throw Exception()
    }
}

private class SpyCategoriesApiService : CategoriesApiService {
    var token = ""

    override suspend fun get(token: String): List<Category> {
        this.token = token
        return emptyList()
    }
}