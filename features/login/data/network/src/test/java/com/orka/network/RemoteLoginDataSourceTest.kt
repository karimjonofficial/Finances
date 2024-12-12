package com.orka.network

import com.orka.lib.BAD_PASSWORD
import com.orka.lib.CREDENTIAL
import com.orka.lib.MainDispatcherContext
import com.orka.lib.PASSWORD
import com.orka.lib.USERNAME
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class RemoteLoginDataSourceTest : MainDispatcherContext() {

    abstract class MockApiServiceContext : MainDispatcherContext() {
        private val apiService = MockLoginApiService()
        val dataSource = RemoteLoginDataSource(apiService)
    }

    @Test
    fun `data source should throw an exception`() = runTest {
        val apiService = StubLoginApiService()
        val dataSource = RemoteLoginDataSource(apiService)

        assertThrows<Exception> { dataSource.getCredential(USERNAME, PASSWORD) }
    }

    @Nested
    inner class MockApiServiceContextImpl : MockApiServiceContext() {
        @Test
        fun returnNullIfNoSuchUsers() = runBlocking {
            assertNull(dataSource.getCredential(USERNAME, BAD_PASSWORD))
        }

        @Test
        fun returnCredentialsIfCorrectInput() = runBlocking {
            assertEquals(CREDENTIAL, dataSource.getCredential(USERNAME, PASSWORD))
        }
    }
}

