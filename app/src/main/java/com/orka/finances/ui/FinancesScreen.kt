@file:JvmName("FinancesScreenKt")

package com.orka.finances.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.orka.finances.application.AppContainer
import com.orka.finances.features.home.presentation.screens.HomeScreen
import com.orka.finances.features.home.presentation.screens.parts.HomeScreenFloatingActionButton
import com.orka.finances.features.home.presentation.screens.parts.HomeScreenTopBar
import com.orka.finances.features.login.presentation.screens.LoginScreen
import com.orka.finances.features.login.presentation.viewmodel.LoginScreenViewModel
import com.orka.finances.ui.navigation.Navigation

@Composable
fun FinancesScreen(
    modifier: Modifier = Modifier,
    container: AppContainer,
    viewModel: AppViewModel
) {
    val uiState = viewModel.uiState.collectAsState()

    when(val authState = uiState.value) {

        AuthenticationState.Initial -> viewModel.initUserInfo()

        AuthenticationState.UnAuthorized -> {
            AppScaffold(modifier = modifier) { innerPadding ->
                val loginDataSource = container.loginDataSource

                val loginViewModel = LoginScreenViewModel(
                    dataSource = loginDataSource,
                    setCredentials = { viewModel.setCredentials(it) }
                )

                LoginScreen(
                    modifier = Modifier.padding(innerPadding),
                    viewModel = loginViewModel
                )
            }
        }

        is AuthenticationState.Authorized -> {
            val navController = rememberNavController()

            NavHost(
                navController = navController,
                startDestination = Navigation.Home,
                modifier = modifier
            ) {
                composable<Navigation.Home> {
                    AppScaffold(
                        topBar = { HomeScreenTopBar { viewModel.unauthorize() } },
                        floatingActionButton = { HomeScreenFloatingActionButton() },
                        modifier = modifier,
                    ) { innerPadding ->
                        val homeScreenViewModel = container.getHomeScreenViewModel(
                            credentialsDataSource = authState.credentialsDataSource,
                            passScreen = { navController.navigate(Navigation.Products(it)) },
                            unauthorize = { viewModel.unauthorize() }
                        )

                        HomeScreen(
                            modifier = Modifier.padding(innerPadding),
                            viewModel = homeScreenViewModel
                        )
                    }
                }

                composable<Navigation.Products> {
                    val products: Navigation.Products = it.toRoute()

                    Box(Modifier.fillMaxSize()) {
                        Text(
                            text = products.categoryId.toString(),
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun AppScaffold(
    modifier: Modifier = Modifier,
    topBar: @Composable () -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = topBar,
        floatingActionButton = floatingActionButton
    ) { innerPadding ->
        content(innerPadding)
    }
}