package com.orka.finances.features.login.viewmodels

import com.orka.finances.Counter
import com.orka.finances.MainDispatcherRule
import com.orka.finances.features.login.data.sources.LoginDataSource
import com.orka.finances.features.login.presentation.viewmodel.LoginScreenState
import com.orka.finances.features.login.presentation.viewmodel.LoginScreenViewModel
import com.orka.finances.lib.data.credentials.Credentials
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.experimental.runners.Enclosed
import org.junit.runner.RunWith

private const val BLANK = "  "
private const val USERNAME = "admin"
private const val PASSWORD = "123"
private const val ACCESS = "access"
private const val REFRESH = "refresh"

@RunWith(Enclosed::class)
class LoginScreenViewModelTest {

    abstract class MainDispatcherContext {
        @get:Rule
        val mainDispatcherRule = MainDispatcherRule()
    }

    abstract class CounterContext : MainDispatcherContext() {
        lateinit var counter: Counter

        @Before
        fun setupCounter() {
            counter = Counter()
        }
    }

    abstract class DummyDataSourceContext : MainDispatcherContext() {
        lateinit var dataSource: LoginDataSource

        @Before
        fun setupDataSource() {
            dataSource = DummyLoginDataSource()
        }
    }

    class CounterContextImpl : CounterContext() {
        @Test
        fun emptyInput() {
            val dataSource = DummyLoginDataSource()
            val viewModel = LoginScreenViewModel(dataSource) { counter.count() }
            val count = counter.count

            viewModel.login(BLANK, BLANK)
            assertEquals(count, counter.count)
        }

        @Test
        fun notSetCredentialsIfUserNotFound() {
            val dataSource = StubLoginDataSourceWithNoCredentials()
            val viewModel = LoginScreenViewModel(dataSource) { counter.count() }
            val count = counter.count

            viewModel.login(USERNAME, PASSWORD)
            assertEquals(count, counter.count)
        }

        @Test
        fun setCredentialsIfUserFound() {
            val dataSource = StubLoginDataSourceWithCredentials()
            val viewModel = LoginScreenViewModel(dataSource) {
                counter.count()
            }

            val count = counter.count

            viewModel.login(USERNAME, PASSWORD)
            assertEquals(count + 1, counter.count)
        }
    }

    class CounterWithDummyDataSourceContext : CounterContext() {
        lateinit var dataSource: LoginDataSource

        @Before
        fun setupDataSource() {
            dataSource = DummyLoginDataSource()
        }


    }

    class DummyDataSourceContextImpl : DummyDataSourceContext() {
        @Test
        fun hasBasicStateWhenCreated() {
            val viewModel = LoginScreenViewModel(dataSource){}
            val state = viewModel.uiState.value

            assertEquals(state, LoginScreenState())
        }
    }
}

private class DummyLoginDataSource : LoginDataSource {
    override suspend fun getCredentials(username: String, password: String): Credentials? {
        return null
    }
}

private class StubLoginDataSourceWithCredentials : LoginDataSource {
    override suspend fun getCredentials(username: String, password: String): Credentials {
        return Credentials(ACCESS, REFRESH)
    }
}

private class StubLoginDataSourceWithNoCredentials : LoginDataSource {
    override suspend fun getCredentials(username: String, password: String): Credentials? {
        return null
    }
}
