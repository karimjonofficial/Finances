package com.orka.finances.features.home.data

import com.orka.finances.CREDENTIAL
import com.orka.finances.DESCRIPTION
import com.orka.finances.NAME
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
        val dataSource = RemoteCategoriesDataSource(apiService, CREDENTIAL)
        dataSource.get()
        assertEquals("Bearer $TOKEN", apiService.authorizationHeader)
    }

    @Test
    fun throwsWhenRequestFailed() = runTest {
        val apiService = StubCategoriesApiService()
        val dataSource = RemoteCategoriesDataSource(apiService, CREDENTIAL)
        assertThrows<Exception> { dataSource.get() }
    }

    @Test
    fun `When get, call get`() = runTest {
        val apiService = SpyCategoriesApiService()
        val dataSource = RemoteCategoriesDataSource(apiService, CREDENTIAL)
        dataSource.get()
        assertTrue(apiService.getCalled)
    }

    @Test
    fun `When add, call post`() = runTest {
        val apiService = SpyCategoriesApiService()
        val dataSource = RemoteCategoriesDataSource(apiService, CREDENTIAL)
        dataSource.add(NAME, DESCRIPTION)
        assertTrue(apiService.postCalled)
    }
}

