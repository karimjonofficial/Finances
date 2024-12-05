package com.orka.finances.features.login.viewmodels

import com.orka.finances.BLANK_LINE
import com.orka.finances.Counter
import com.orka.finances.MainDispatcherContext
import com.orka.finances.PASSWORD
import com.orka.finances.USERNAME
import com.orka.finances.features.login.presentation.viewmodel.LoginScreenState
import com.orka.finances.features.login.presentation.viewmodel.LoginScreenViewModel
import org.junit.jupiter.api.Assertions.assertEquals
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
    }

    @Test
    fun hasBasicStateWhenCreated() {
        val dataSource = DummyLoginDataSource()
        val viewModel = LoginScreenViewModel(dataSource) {}
        val state = viewModel.uiState.value

        assertEquals(state, LoginScreenState())
    }

    @Nested
    inner class CounterContextImpl : CounterContext() {

        @Test
        fun notSetCredentialsIfUserNotFound() {
            val dataSource = StubLoginDataSourceWithNoCredentials()
            val count = counter.count
            val viewModel = LoginScreenViewModel(dataSource) { counter.count() }
            viewModel.login(USERNAME, PASSWORD)
            assertEquals(count, counter.count)
        }

        @Nested
        inner class WithStubDataSourceAndViewModel : WithStubDataSourceWithCredentialsAndCountingViewModel() {

            @Test
            fun emptyInput() {
                viewModel.login(BLANK_LINE, BLANK_LINE)
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

