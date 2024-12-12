package com.orka.network

import com.orka.lib.CREDENTIAL
import com.orka.lib.DESCRIPTION
import com.orka.lib.NAME
import com.orka.lib.TOKEN
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

