@file:JvmName("FinancesScreenKt")

package com.orka.finances.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.orka.finances.application.AppContainer
import com.orka.finances.features.home.presentation.screens.HomeScreen
import com.orka.finances.features.home.presentation.screens.parts.AddCategoryDialog
import com.orka.finances.features.home.presentation.screens.parts.HomeScreenFloatingActionButton
import com.orka.finances.features.home.presentation.screens.parts.HomeScreenTopBar
import com.orka.finances.features.login.presentation.screens.LoginScreen
import com.orka.finances.features.login.presentation.viewmodel.LoginScreenViewModel
import com.orka.finances.features.stock.presentation.screens.StockScreen
import com.orka.finances.ui.navigation.Navigation

@Composable
fun FinancesScreen(modifier: Modifier = Modifier, container: AppContainer, appViewModel: AppViewModel) {
    val uiState = appViewModel.uiState.collectAsState()

    when(val authState = uiState.value) {

        AuthenticationState.Initial -> appViewModel.initUserInfo()

        AuthenticationState.UnAuthorized -> {
            AppScaffold(modifier = modifier) { innerPadding ->
                val loginDataSource = container.loginDataSource

                val loginViewModel = LoginScreenViewModel(
                    dataSource = loginDataSource,
                    setCredential = { appViewModel.setCredentials(it) }
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

                    val dialogVisible = rememberSaveable { mutableStateOf(false) }

                    AppScaffold(
                        topBar = { HomeScreenTopBar { appViewModel.unauthorize() } },
                        floatingActionButton = { HomeScreenFloatingActionButton { dialogVisible.value = true } },
                        modifier = modifier,
                    ) { innerPadding ->
                        val homeScreenViewModel = container.getHomeScreenViewModel(
                            credential = authState.credential,
                            navigate = { navController.navigate(Navigation.Stock(it)) },
                            unauthorize = { appViewModel.unauthorize() }
                        )

                        if(dialogVisible.value) {
                            AddCategoryDialog(
                                modifier = Modifier.fillMaxWidth(),
                                dismissRequest = { dialogVisible.value = false },
                                addClick = { name, description ->
                                    homeScreenViewModel.addCategory(name, description)
                                    dialogVisible.value = false
                                }
                            )
                        }

                        HomeScreen(
                            modifier = Modifier.padding(innerPadding),
                            viewModel = homeScreenViewModel
                        )
                    }
                }

                composable<Navigation.Stock> {
                    val destination: Navigation.Stock = it.toRoute()

                    val stockScreenViewModel = container.getStockScreenViewModel(
                        credential = authState.credential,
                        categoryId = destination.categoryId,
                        navigate = {},
                        unauthorize = { appViewModel.unauthorize() }
                    )

                    stockScreenViewModel.fetchData()

                    StockScreen(viewModel = stockScreenViewModel)
                }

                composable<Navigation.StockItem> {
                    val destination: Navigation.StockItem = it.toRoute()

                    Surface {
                        Text(destination.itemId.toString())
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