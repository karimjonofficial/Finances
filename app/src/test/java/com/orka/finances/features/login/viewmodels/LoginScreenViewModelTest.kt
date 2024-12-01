package com.orka.finances.features.login.viewmodels

import com.orka.finances.Counter
import com.orka.finances.MainDispatcherRule
import com.orka.finances.features.login.data.sources.LoginDataSource
import com.orka.finances.features.login.presentation.viewmodel.LoginScreenViewModel
import com.orka.finances.lib.data.credentials.Credentials
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

private const val BLANK = "  "
private const val USERNAME = "admin"
private const val PASSWORD = "123"
private const val ACCESS = "access"
private const val REFRESH = "refresh"

class LoginScreenViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun emptyInput() {
        val dataSource = DummyLoginDataSource()
        val counter = Counter()
        val viewModel = LoginScreenViewModel(dataSource) { counter.count() }
        val count = counter.count

        viewModel.login(BLANK, BLANK)
        assertEquals(count, counter.count)
    }

    @Test
    fun noPassIfUserNotFound() {
        val dataSource = DummyLoginDataSource()
        val counter = Counter()
        val viewModel = LoginScreenViewModel(dataSource) { counter.count() }
        val count = counter.count

        viewModel.login(USERNAME, PASSWORD)
        assertEquals(count, counter.count)
    }

    @Test
    fun passScreenIfUserFound() {
        val dataSource = StubLoginDataSource()
        val counter = Counter()
        val viewModel = LoginScreenViewModel(dataSource) { counter.count() }
        val count = counter.count

        viewModel.login(USERNAME, PASSWORD)
        assertEquals(count + 1, counter.count)
    }
}

private class DummyLoginDataSource : LoginDataSource {
    override suspend fun getCredentials(username: String, password: String): Credentials? {
        return null
    }
}

private class StubLoginDataSource : LoginDataSource {
    override suspend fun getCredentials(username: String, password: String): Credentials {
        return Credentials(ACCESS, REFRESH)
    }
}
