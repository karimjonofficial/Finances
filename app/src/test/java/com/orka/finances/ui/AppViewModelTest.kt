package com.orka.finances.ui

import com.orka.finances.CREDENTIAL
import com.orka.finances.MainDispatcherContext
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class AppViewModelTest : MainDispatcherContext() {

    abstract class SpyDataSourceSelectWithViewModelContext {
        val dataSource = SpyUserInfoDataSourceSelect()
        val viewModel = AppViewModel(dataSource)
    }

    @Test
    fun setsStateUnauthorizedIfNoInfo() {
        val dataSource = StubUserInfoDataSourceWithNoInfo()
        val viewModel = AppViewModel(dataSource)

        viewModel.initialize()
        val state = viewModel.uiState.value

        assertEquals(AuthenticationState.UnAuthorized, state)
    }

    @Test
    fun setsStateUnauthorizedIfNoCredentials() {
        val dataSource = StubUserInfoDataSourceWithNoToken()
        val viewModel = AppViewModel(dataSource)

        viewModel.initialize()
        val state = viewModel.uiState.value

        assertEquals(AuthenticationState.UnAuthorized, state)
    }

    @Test
    fun setsStateAuthorizedIfCredentialsExist() {
        val dataSource = StubUserInfoDataSourceWithInfo()
        val viewModel = AppViewModel(dataSource)

        viewModel.initialize()
        val state = viewModel.uiState.value

        assertTrue(state is AuthenticationState.Authorized)
    }

    @Nested
    inner class SpyDataSourceSelectWithViewModelContextImpl : SpyDataSourceSelectWithViewModelContext() {

        @Test
        fun fetchesUserInfoFromDbInInit() {
            viewModel.initialize()
            assertTrue(dataSource.called)
        }

        @Test
        fun getsUserInfo() {
            viewModel.setCredentials(CREDENTIAL)
            assertTrue(dataSource.called)
        }
    }
}