package com.orka.finances.ui

import com.orka.finances.CREDENTIAL
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class AppViewModelTest : MainDispatcherContext() {

    abstract class SpyDataSourceSelectWithViewModelContext {
        val dataSource = SpyUserInfoDataSourceOfSelect()
        val viewModel = AppViewModel(dataSource)
    }
    abstract class StubDataSourceWithNoInfoContext {
        private val dataSource = StubUserInfoDataSourceWithNoInfo()
        val viewModel = AppViewModel(dataSource)
    }

    @Test
    fun setsStateUnauthorizedIfNoCredentials() {
        val dataSource = StubUserInfoDataSourceWithNoToken()
        val viewModel = AppViewModel(dataSource)

        viewModel.initUserInfo()
        val state = viewModel.uiState.value

        assertEquals(AuthenticationState.UnAuthorized, state)
    }

    @Test
    fun setsStateAuthorizedIfCredentialsExist() {
        val dataSource = StubUserInfoDataSourceWithInfo()
        val viewModel = AppViewModel(dataSource)

        viewModel.initUserInfo()
        val state = viewModel.uiState.value

        assertTrue(state is AuthenticationState.Authorized)
    }

    @Test
    fun insertsSetCredentialsCalledWhenNoInfo() {
        val dataSource = SpyUserInfoDataSourceOfInsert()
        val viewModel = AppViewModel(dataSource)

        viewModel.setCredentials(CREDENTIAL)

        assertTrue(dataSource.called)
    }

    @Nested
    inner class StubDataSourceWithNoInfoContextImpl : StubDataSourceWithNoInfoContext() {

        @Test
        fun setsStateUnauthorizedIfNoInfo() {
            viewModel.initUserInfo()
            val state = viewModel.uiState.value

            assertEquals(AuthenticationState.UnAuthorized, state)
        }
    }

    @Nested
    inner class SpyDataSourceSelectWithViewModelContextImpl : SpyDataSourceSelectWithViewModelContext() {

        @Test
        fun fetchesUserInfoFromDbInInit() {
            viewModel.initUserInfo()
            assertTrue(dataSource.called)
        }

        @Test
        fun getsUserInfo() {
            viewModel.setCredentials(CREDENTIAL)
            assertTrue(dataSource.called)
        }
    }
}