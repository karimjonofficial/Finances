package com.orka.finances.features.home.data

import com.orka.finances.TOKEN
import com.orka.finances.features.home.data.sources.network.RemoteCategoriesDataSource
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

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
    fun throwsWhenRequestFailed() = runTest {
        val apiService = StubCategoriesApiService()
        val dataSource = RemoteCategoriesDataSource(apiService)

        assertThrows<Exception> {
            dataSource.get(TOKEN)
        }
    }
}

