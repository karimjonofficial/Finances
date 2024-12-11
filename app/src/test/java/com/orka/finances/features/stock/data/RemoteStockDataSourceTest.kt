package com.orka.finances.features.stock.data

import com.orka.finances.CREDENTIAL
import com.orka.finances.ID
import com.orka.finances.MainDispatcherContext
import com.orka.finances.TOKEN
import com.orka.finances.features.stock.data.sources.network.RemoteStockDataSource
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class RemoteStockDataSourceTest : MainDispatcherContext() {

    abstract class SpyStockApiServiceContext {
        val apiService = SpyStockApiService()
        val dataSource = RemoteStockDataSource(apiService, CREDENTIAL)
    }

    @Test
    fun `When thrown, throw`() = runTest {
        val apiService = ThrowingStubStockApiService()
        val dataSource = RemoteStockDataSource(apiService, CREDENTIAL)
        assertThrows<Exception> { dataSource.get(ID) }
    }

    @Nested
    inner class SpyStockApiContext : SpyStockApiServiceContext() {

        @Test
        fun `When get, call get`() = runTest {
            dataSource.get(ID)
            assertTrue(apiService.getCalled)
        }

        @Test
        fun `When get, pass credential`() = runTest {
            dataSource.get(ID)
            assertEquals("Bearer $TOKEN", apiService.authHeader)
        }

        @Test
        fun `When get, pass categoryId`() = runTest {
            dataSource.get(ID)
            assertEquals(ID, apiService.categoryId)
        }
    }
}
