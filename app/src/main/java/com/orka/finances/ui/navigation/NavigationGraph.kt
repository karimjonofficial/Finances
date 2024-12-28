package com.orka.finances.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.orka.basket.BasketScreen
import com.orka.finances.ui.ProductScreenMock
import com.orka.history.HistoryScreen
import com.orka.home.HomeScreen
import com.orka.main.MainStates
import com.orka.warehouse.WarehouseScreen

@Composable
internal fun NavigationGraph(
    modifier: Modifier = Modifier,
    state: MainStates.WithSingleton.WithCredential.WithContainers,
    navController: NavHostController
) {

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Navigation.Home
    ) {

        composable<Navigation.Home> {

            HomeScreen(
                viewModel = state.scopedContainer.homeScreenViewModel,
                navigateToHistory = { navController.navigateToHistory() },
                navigateToBasket = { navController.navigateToBasket() }
            )
        }

        composable<Navigation.Basket> {

            BasketScreen(
                viewModel = state.scopedContainer.basketViewModel,
                navigateToHome = { navController.navigateToHome() },
                navigateToHistory = { navController.navigateToHistory() },
                formatter = state.singletonContainer.formatter
            )
        }

        composable<Navigation.History> {

            HistoryScreen(
                viewModel = state.scopedContainer.historyViewModel,
                navigateToHome = { navController.navigateToHome() },
                navigateToBasket = { navController.navigateToBasket() },
                formatter = state.singletonContainer.formatter
            )
        }

        composable<Navigation.Warehouse> {

            val destination: Navigation.Warehouse = it.toRoute()

            WarehouseScreen(
                stockScreenViewModel = state.transientContainer
                    .stockScreenViewModel(destination.categoryId),
                productsScreenViewModel = state.transientContainer
                    .productsViewModel(destination.categoryId),
                formatter = state.singletonContainer.formatter
            )
        }

        composable<Navigation.Product> {
            ProductScreenMock()
        }
    }
}