package com.orka.finances.features.login.data.sources.network

import com.orka.finances.lib.data.credentials.Credentials
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

private const val USERNAME = "admin"
private const val PASSWORD = "123"
private const val FAKE_PASSWORD = "1234"
private val CREDENTIALS = Credentials("access", "token")

class RemoteLoginDataSourceTest {
    @Test
    fun returnNullIfFails() = runBlocking {
        val apiService = DummyLoginApiService()
        val dataSource = RemoteLoginDataSource(apiService)
        var thrown = false
        try {
            dataSource.getCredentials(USERNAME, PASSWORD)
        } catch(e: Exception) {
            thrown = true
        }
        assertTrue(thrown)
    }

    @Test
    fun returnNullIfNoSuchUsers() = runBlocking {
        val apiService = StubLoginApiService()
        val dataSource = RemoteLoginDataSource(apiService)
        assertNull(dataSource.getCredentials(USERNAME, FAKE_PASSWORD))
    }

    @Test
    fun returnCredentialsIfCorrectInput() = runBlocking {
        val apiService = StubLoginApiService()
        val dataSource = RemoteLoginDataSource(apiService)
        assertEquals(CREDENTIALS, dataSource.getCredentials(USERNAME, PASSWORD))
    }
}

private class StubLoginApiService : LoginApiService {
    override suspend fun getCredentials(username: String, password: String): Credentials? {
        return if(username == USERNAME && password == PASSWORD) CREDENTIALS else null
    }
}

private class DummyLoginApiService : LoginApiService {
    override suspend fun getCredentials(username: String, password: String): Credentials? {
        throw Exception()
    }
}
