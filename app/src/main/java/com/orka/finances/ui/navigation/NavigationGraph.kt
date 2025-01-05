package com.orka.finances.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.orka.basket.BasketScreen
import com.orka.core.Printer
import com.orka.history.HistoryScreen
import com.orka.home.HomeScreen
import com.orka.main.MainStates
import com.orka.product.ProductScreen
import com.orka.warehouse.WarehouseScreen

@Composable
internal fun NavigationGraph(
    modifier: Modifier = Modifier,
    state: MainStates.HasSingleton.HasCredential.HasContainers,
    navController: NavHostController,
    unauthorize: () -> Unit,
    printer: Printer
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
                navigateToBasket = { navController.navigateToBasket() },
                scan = { unauthorize() }
            )
        }

        composable<Navigation.Basket> {

            BasketScreen(
                viewModel = state.scopedContainer.basketViewModel,
                navigateToHome = { navController.navigateToHome() },
                navigateToHistory = { navController.navigateToHistory() },
                formatter = state.singletonContainer.formatter,
                printer = printer
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
                    .stockViewModel(destination.categoryId),
                productsScreenViewModel = state.transientContainer
                    .productsViewModel(destination.categoryId),
                formatter = state.singletonContainer.formatter
            )
        }

        composable<Navigation.Product> {
            val destination: Navigation.Product = it.toRoute()

            ProductScreen(
                viewModel = state.transientContainer.productViewModel(destination.productId),
                formatter = state.singletonContainer.formatter
            ) { categoryId ->
                state.transientContainer.productsViewModel(categoryId).fetch()
                state.transientContainer.stockViewModel(categoryId).fetch()
            }
        }
    }
}