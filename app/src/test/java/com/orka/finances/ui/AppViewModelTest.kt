package com.orka.finances.ui

import com.orka.finances.MainDispatcherRule
import com.orka.finances.lib.data.credentials.Credentials
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

private val CREDENTIALS = Credentials("token", "access")
class AppViewModelTest {
    @get: Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun fetchesUserInfoFromDbInInit() {
        val dataSource = SpyUserInfoDataSourceSelect()
        val count = dataSource.counter.count
        val viewModel = AppViewModel(dataSource)

        viewModel.initialize()

        assertEquals(count + 1, dataSource.counter.count)
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
        val dataSource = StubUserInfoDataSource()
        val viewModel = AppViewModel(dataSource)

        viewModel.initialize()
        val state = viewModel.uiState.value

        assertTrue(state is AuthenticationState.Authorized)
    }

    @Test
    fun getsUserInfo() {
        val dataSource = SpyUserInfoDataSourceSelect()
        val viewModel = AppViewModel(dataSource)
        val count = dataSource.counter.count

        viewModel.setCredentials(CREDENTIALS)

        assertEquals(count + 1, dataSource.counter.count)
    }

    //TODO Write tests for setCredentials
}