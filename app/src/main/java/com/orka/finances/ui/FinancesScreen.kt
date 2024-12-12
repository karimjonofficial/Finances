//@file:JvmName("FinancesScreenKt")

package com.orka.finances.ui

import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.orka.composables.LoginScreen
import com.orka.composables.screens.HomeScreen
import com.orka.composables.screens.StockScreen
import com.orka.di.AppContainer
import com.orka.finances.ui.navigation.Navigation
import com.orka.viewmodels.AppViewModel
import com.orka.viewmodels.AuthenticationState

@Composable
fun FinancesScreen(
    modifier: Modifier = Modifier,
    container: AppContainer,
    appViewModel: AppViewModel
) {
    val uiState = appViewModel.uiState.collectAsState()

    when (val authState = uiState.value) {

        AuthenticationState.Initial -> appViewModel.initUserInfo()

        AuthenticationState.UnAuthorized -> {
            LoginScreen(viewModel = container.getLoginScreenViewModel())
        }

        is AuthenticationState.Authorized -> {
            val navController = rememberNavController()

            NavHost(
                navController = navController,
                startDestination = Navigation.Home,
                modifier = modifier
            ) {
                composable<Navigation.Home> {

                    val viewModel = container.getHomeScreenViewModel(
                        credential = authState.credential,
                        navigate = { navController.navigate(Navigation.Stock(it)) }
                    )

                    HomeScreen(viewModel = viewModel) { appViewModel.unauthorize() }
                }

                composable<Navigation.Stock> {
                    val destination: Navigation.Stock = it.toRoute()

                    val stockScreenViewModel = container.getStockScreenViewModel(
                        credential = authState.credential,
                        categoryId = destination.categoryId,
                        navigate = {}
                    )

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