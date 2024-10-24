package com.orka.finances.login.data.sources.network

import com.orka.finances.features.login.data.models.UserCredentials
import com.orka.finances.features.login.data.sources.network.LoginApiService
import com.orka.finances.features.login.data.sources.network.RemoteLoginDataSource
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

private const val USERNAME = "admin"
private const val PASSWORD = "123"
private const val FAKE_PASSWORD = "1234"
private val CREDENTIALS = UserCredentials("access", "token")

class RemoteLoginDataSourceTest {
    @Test
    fun returnNullIfFails() = runBlocking {
        val apiService = DummyLoginApiService()
        val dataSource = RemoteLoginDataSource(apiService)
        assertNull(dataSource.getCredentials(USERNAME, PASSWORD))
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
    override suspend fun getCredentials(username: String, password: String): UserCredentials? {
        return if(username == USERNAME && password == PASSWORD) CREDENTIALS else null
    }
}

private class DummyLoginApiService : LoginApiService {
    override suspend fun getCredentials(username: String, password: String): UserCredentials? {
        throw Exception()
    }
}
