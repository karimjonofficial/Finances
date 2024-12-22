package com.orka.finances.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.orka.basket.BasketScreen
import com.orka.credentials.Credential
import com.orka.di.SingletonContainer
import com.orka.finances.ui.StockItemScreenMock
import com.orka.history.HistoryScreen
import com.orka.home.HomeScreen
import com.orka.warehouse.WarehouseScreen

@Composable
internal fun NavigationGraph(
    modifier: Modifier = Modifier,
    singletonContainer: SingletonContainer,
    credential: Credential
) {

    val navController = rememberNavController()

    val scopedContainer = singletonContainer.scopedContainer(
        credential = credential,
        navigateToWarehouse = { navController.navigateToWarehouse(it) }
    )

    val transientContainer = scopedContainer.transientContainer(
        navigateToStockItem = { navController.navigateToStockItem(it) }
    )

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Navigation.Home
    ) {

        composable<Navigation.Home> {

            HomeScreen(
                viewModel = scopedContainer.homeScreenViewModel,
                navigateToHistory = { navController.navigateToHistory() },
                navigateToBasket = { navController.navigateToBasket() }
            )
        }

        composable<Navigation.Basket> {

            BasketScreen(
                viewModel = scopedContainer.basketViewModel,
                navigateToHome = { navController.navigateToHome() },
                navigateToHistory = { navController.navigateToHistory() },
                formatter = singletonContainer.formatter
            )
        }

        composable<Navigation.History> {

            HistoryScreen(
                viewModel = scopedContainer.historyViewModel,
                navigateToHome = { navController.navigateToHome() },
                navigateToBasket = { navController.navigateToBasket() },
                formatter = singletonContainer.formatter
            )
        }

        composable<Navigation.Warehouse> {

            val destination: Navigation.Warehouse = it.toRoute()

            WarehouseScreen(
                stockScreenViewModel = transientContainer
                    .stockScreenViewModel(destination.categoryId),
                productsScreenViewModel = transientContainer
                    .productsViewModel(destination.categoryId),
                formatter = singletonContainer.formatter
            )
        }

        composable<Navigation.StockItem> {

            StockItemScreenMock()
        }
    }
}