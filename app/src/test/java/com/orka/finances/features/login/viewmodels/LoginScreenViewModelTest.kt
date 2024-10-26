package com.orka.finances.features.login.viewmodels

import com.orka.finances.MainDispatcherRule
import com.orka.finances.lib.data.UserCredentials
import com.orka.finances.features.login.data.sources.LoginDataSource
import com.orka.finances.features.login.presentation.viewmodel.LoginScreenViewModel
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
        val viewModel = LoginScreenViewModel(dataSource) { spyPassScreen() }
        val count = calls

        viewModel.login(BLANK, BLANK)
        assertEquals(count, calls)
    }

    @Test
    fun noPassIfUserNotFound() {
        val dataSource = DummyLoginDataSource()
        val viewModel = LoginScreenViewModel(dataSource) { spyPassScreen() }
        val count = calls

        viewModel.login(USERNAME, PASSWORD)
        assertEquals(count, calls)
    }

    @Test
    fun passScreenIfUserFound() {
        val dataSource = StubLoginDataSource()
        val viewModel = LoginScreenViewModel(dataSource) { spyPassScreen() }
        val count = calls

        viewModel.login(USERNAME, PASSWORD)
        assertEquals(count + 1, calls)
    }
}

private class StubLoginDataSource : LoginDataSource {
    override suspend fun getCredentials(username: String, password: String): UserCredentials {
        return UserCredentials(ACCESS, REFRESH)
    }
}

private class DummyLoginDataSource : LoginDataSource {
    override suspend fun getCredentials(username: String, password: String): UserCredentials? {
        return null
    }
}

var calls = 0
private fun spyPassScreen() {
    calls++
}
