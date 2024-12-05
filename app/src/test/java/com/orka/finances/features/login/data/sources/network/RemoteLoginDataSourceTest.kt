package com.orka.finances.features.login.data.sources.network

import com.orka.finances.CREDENTIAL
import com.orka.finances.BAD_PASSWORD
import com.orka.finances.PASSWORD
import com.orka.finances.USERNAME
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class RemoteLoginDataSourceTest {

    abstract class MockApiServiceContext {
        private val apiService = MockLoginApiService()
        val dataSource = RemoteLoginDataSource(apiService)
    }

    @Test
    fun `data source should throw an exception`() = runTest {
        val apiService = StubLoginApiService()
        val dataSource = RemoteLoginDataSource(apiService)

        assertThrows<Exception> { dataSource.getCredentials(USERNAME, PASSWORD) }
    }

    @Nested
    inner class MockApiServiceContextImpl : MockApiServiceContext() {
        @Test
        fun returnNullIfNoSuchUsers() = runBlocking {
            assertNull(dataSource.getCredentials(USERNAME, BAD_PASSWORD))
        }

        @Test
        fun returnCredentialsIfCorrectInput() = runBlocking {
            assertEquals(CREDENTIAL, dataSource.getCredentials(USERNAME, PASSWORD))
        }
    }
}

