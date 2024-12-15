package com.orka.network

import com.orka.lib.CREDENTIAL
import com.orka.lib.DESCRIPTION
import com.orka.lib.ID
import com.orka.lib.IMG_SRC
import com.orka.lib.MainDispatcherContext
import com.orka.lib.NAME
import com.orka.lib.PRICE
import com.orka.lib.TOKEN
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import retrofit2.HttpException

class RemoteProductsDataSourceTest : MainDispatcherContext() {

    abstract class SpyProductsApiServiceContext {
        val apiService = SpyProductsApiService()
        val dataSource = RemoteProductsDataSource(apiService, CREDENTIAL)
    }
    abstract class StubProductsApiServiceContext {
        private val apiService = StubProductsApiService()
        val dataSource = RemoteProductsDataSource(apiService, CREDENTIAL)
    }

    @Test
    fun `When throw, throw`() = runTest {
        val apiService = ThrowingStubProductsApiService()
        val dataSource = RemoteProductsDataSource(apiService, CREDENTIAL)
        assertThrows<HttpException> {
            dataSource.get(ID)
        }
    }

    @Nested
    inner class StubProductsApiContext : StubProductsApiServiceContext() {

        @Test
        fun `When get, return what api return`() = runTest {
            assertNull(dataSource.get(ID))
        }

        @Test
        fun `When add, return what api return`() = runTest {
            assertNull(dataSource.add(NAME, PRICE, DESCRIPTION, IMG_SRC))
        }

    }

    @Nested
    inner class SpyProductsApiContext : SpyProductsApiServiceContext() {

        @Test
        fun `When get, call get`() = runTest {
            dataSource.get(ID)
            assertTrue(apiService.getCalled)
        }

        @Test
        fun `When get, pass header`() = runTest {
            dataSource.get(ID)
            assertEquals("Bearer $TOKEN", apiService.header)
        }

        @Test
        fun `When add, call post`() = runTest {
            dataSource.add(NAME, PRICE, DESCRIPTION, IMG_SRC)
            assertTrue(apiService.postCalled)
        }
    }
}