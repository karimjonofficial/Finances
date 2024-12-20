package com.orka.finances.ui

import android.icu.text.DecimalFormat
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.orka.di.AppContainer
import com.orka.finances.ui.navigation.Navigation
import com.orka.history.HistoryScreen
import com.orka.home.HomeScreen
import com.orka.login.LoginScreen
import com.orka.main.AuthenticationState
import com.orka.main.MainViewModel
import com.orka.res.Strings
import com.orka.warehouse.WarehouseScreen

@Composable
fun FinancesScreen(
    modifier: Modifier = Modifier,
    container: AppContainer,
    appViewModel: MainViewModel
) {
    val uiState = appViewModel.uiState.collectAsState()

    when (val authState = uiState.value) {

        AuthenticationState.Initial -> {
            Surface {
                Box(modifier = Modifier.fillMaxSize()) {
                    Text(
                        text = stringResource(Strings.app_is_initializing),
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }

            appViewModel.initUserInfo()
        }

        AuthenticationState.UnAuthorized -> {
            LoginScreen(viewModel = container.loginScreenViewModel())
        }

        is AuthenticationState.Authorized -> {
            val navController = rememberNavController()
            val format = DecimalFormat("#,###")
            val uzs = stringResource(Strings.uzs)

            val formatCurrency: (Double) -> String = {
                "${format.format(it).replace(",", " ")} $uzs"
            }

            NavHost(
                navController = navController,
                startDestination = Navigation.Home,
                modifier = modifier
            ) {
                composable<Navigation.Home> {

                    val viewModel = container.homeScreenViewModel(authState.credential) {
                        navController.navigate(Navigation.Warehouse(it))
                    }

                    HomeScreen(viewModel = viewModel) {
                        navController.navigate(Navigation.History)
                    }
                }

                composable<Navigation.History> {
                    HistoryScreen(
                        viewModel = container.historyViewModel(authState.credential),
                        navigateToHome = { navController.navigate(Navigation.Home)},
                        formatCurrency = formatCurrency
                    )
                }

                composable<Navigation.Warehouse> {

                    val destination: Navigation.Warehouse = it.toRoute()

                    WarehouseScreen(
                        stockScreenViewModel = container.stockScreenViewModel(
                            credential = authState.credential,
                            categoryId = destination.categoryId,
                            navigate = { id ->
                                navController.navigate(Navigation.StockItem(id))
                            }
                        ),
                        productsScreenViewModel = container.productsViewModel(
                            credential = authState.credential,
                            categoryId = destination.categoryId,
                        ),
                        formatCurrency = formatCurrency
                    )
                }

                composable<Navigation.StockItem> {
                    val destination: Navigation.StockItem = it.toRoute()

                    Surface {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = destination.itemId.toString(),
                                style = MaterialTheme.typography.displayLarge
                            )
                        }
                    }
                }
            }
        }
    }
}