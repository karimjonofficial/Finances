package com.orka.finances.features.home.data

import com.orka.finances.features.home.data.sources.network.CategoriesApiService
import com.orka.finances.features.home.data.sources.network.RemoteCategoriesDataSource
import com.orka.finances.features.home.models.Category
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

private const val TOKEN = "token"

class RemoteCategoriesDataSourceTest {
    @Test
    fun passesTokenToApiService() = runBlocking {
        val apiService = SpyCategoriesApiService()
        val dataSource = RemoteCategoriesDataSource(apiService)
        val expected = "Bearer $TOKEN"
        dataSource.get(TOKEN)
        assertEquals(expected, apiService.token)
    }

    @Test
    fun throwsWhenRequestFailed() = runBlocking {
        var thrown = false

        try {
            val apiService = DummyCategoriesApiService()
            val dataSource = RemoteCategoriesDataSource(apiService)
            dataSource.get(TOKEN)
        } catch(e: Exception) {
            thrown = true
        }

        assertTrue(thrown)
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
}