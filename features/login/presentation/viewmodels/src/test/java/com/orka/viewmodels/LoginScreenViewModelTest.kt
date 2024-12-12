package com.orka.viewmodels

import com.orka.lib.BLANK_LINE
import com.orka.lib.Counter
import com.orka.lib.MainDispatcherContext
import com.orka.lib.PASSWORD
import com.orka.lib.USERNAME
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class LoginScreenViewModelTest : MainDispatcherContext() {

    abstract class CounterContext {
        val counter = Counter()

        abstract class WithStubDataSourceWithCredentialsAndCountingViewModel : CounterContext() {
            private val dataSource = StubLoginDataSourceWithCredentials()
            val count = counter.count
            val viewModel = LoginScreenViewModel(dataSource) { counter.count() }
        }

        abstract class WithStubDataSourceWithNoCredentials : CounterContext() {
            val dataSource = StubLoginDataSourceWithNoCredentials()
        }
    }
    abstract class StubDataSourceWithNoCredentialsContext {
        private val dataSource = StubLoginDataSourceWithNoCredentials()
        val viewModel = LoginScreenViewModel(dataSource) {}
    }
    abstract class ThrowingStubDataSourceContext {
        private val dataSource = ThrowingStubLoginDataSource()
        val viewModel = LoginScreenViewModel(dataSource) {}
    }

    @Test
    fun hasInitialStateWhenCreated() {
        val dataSource = DummyLoginDataSource()
        val viewModel = LoginScreenViewModel(dataSource) {}
        val state = getState(viewModel)

        assertEquals(state, LoginScreenState.Initial)
    }

    @Nested
    inner class ThrowingStubDataSourceContextImpl : ThrowingStubDataSourceContext() {

        @Test
        fun setsStateFailedWhenThrow() {
            viewModel.login(USERNAME, PASSWORD)
            assertTrue(getState(viewModel) is LoginScreenState.Failed)
        }

        @Test
        fun resetsFailureStateAutoInThreeSecWhenThrow() = runTest {
            viewModel.login(USERNAME, PASSWORD)
            val state = withContext(Dispatchers.Default) {
                delay(4000)
                getState(viewModel)
            }
            assertEquals(state, LoginScreenState.Initial)
        }
    }

    @Nested
    inner class StubDataSourceWithNoCredentialContextImpl : StubDataSourceWithNoCredentialsContext() {

        @Test
        fun setsStateFailedWhenUserNotFound() {
            viewModel.login(USERNAME, PASSWORD)
            assertTrue(getState(viewModel) is LoginScreenState.Failed)
        }

        @Test
        fun resetsFailureState() {
            viewModel.login(USERNAME, PASSWORD)
            viewModel.resetState()
            assertEquals(getState(viewModel), LoginScreenState.Initial)
        }

        @Test
        fun resetsFailureStateAutoInThreeSecWhenUserNotFound() = runTest {
            viewModel.login(USERNAME, PASSWORD)
            val state = withContext(Dispatchers.Default) {
                delay(4000)
                getState(viewModel)
            }
            assertEquals(state, LoginScreenState.Initial)
        }
    }

    @Nested
    inner class CounterContextImpl : CounterContext() {

        @Nested
        inner class WithEmptyStubDataSource : WithStubDataSourceWithNoCredentials() {

            @Test
            fun notSetCredentialsIfUserNotFound() {
                val count = counter.count
                val viewModel = LoginScreenViewModel(dataSource) { counter.count() }
                viewModel.login(USERNAME, PASSWORD)
                assertEquals(count, counter.count)
            }
        }

        @Nested
        inner class WithStubDataSourceAndViewModel : WithStubDataSourceWithCredentialsAndCountingViewModel() {

            @Test
            fun emptyInput() {
                viewModel.login(BLANK_LINE, BLANK_LINE)
                assertEquals(count, counter.count)
            }

            @Test
            fun `When username blank, no calls`() {
                viewModel.login(BLANK_LINE, PASSWORD)
                assertEquals(count, counter.count)
            }

            @Test
            fun `When password blank, no calls`() {
                viewModel.login(USERNAME, BLANK_LINE)
                assertEquals(count, counter.count)
            }

            @Test
            fun setCredentialsIfUserFound() {
                viewModel.login(USERNAME, PASSWORD)
                assertEquals(count + 1, counter.count)
            }
        }
    }
}

private fun getState(viewModel: LoginScreenViewModel) = viewModel.uiState.value