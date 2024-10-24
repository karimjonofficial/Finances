package com.orka.finances.login.viewmodels

import com.orka.finances.features.login.data.models.UserCredentials
import com.orka.finances.features.login.data.sources.LoginDataSource
import com.orka.finances.features.login.presentation.viewmodel.LoginScreenViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description

private const val BLANK = "  "
private const val USERNAME = "admin"
private const val PASSWORD = "123"
private const val ACCESS = "access"
private const val REFRESH = "refresh"

class MainDispatcherRule @OptIn(ExperimentalCoroutinesApi::class) constructor(
    private val testDispatcher: TestDispatcher = UnconfinedTestDispatcher(),
) : TestWatcher() {
    @OptIn(ExperimentalCoroutinesApi::class)
    override fun starting(description: Description) {
        Dispatchers.setMain(testDispatcher)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun finished(description: Description) {
        Dispatchers.resetMain()
    }
}

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
