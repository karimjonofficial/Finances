package com.orka.finances.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.orka.basket.BasketScreen
import com.orka.core.Printer
import com.orka.history.HistoryScreen
import com.orka.home.HomeScreen
import com.orka.main.AppStates
import com.orka.product.ProductScreen
import com.orka.warehouse.WarehouseScreen

@Composable
internal fun NavigationGraph(
    modifier: Modifier = Modifier,
    state: AppStates.HasSingleton.HasCredential.HasContainers,
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
            val viewModel = state.scopedContainer.warehouseViewModel(destination.categoryId)
            val productsState = viewModel.productsUiState.collectAsState()
            val stockState = viewModel.stockUiState.collectAsState()

            WarehouseScreen(
                formatter = state.singletonContainer.formatter,
                productsContentState = productsState.value,
                stockContentState = stockState.value,
                initializeProductsContent = { this.initialize(viewModel) },
                initializeStockItemsContent = { this.initialize(viewModel) },
                processProductsContent = { this.process(viewModel) },
                processStockContent = { this.process(viewModel) },
                selectProduct = { product -> this.selectProduct(product, viewModel) },
                addToBasket = { stockItem -> this.addToBasket(stockItem, viewModel) },
                addReceive = { productId, amount, price, comment ->
                    stockState.value.receive(productId, amount, price, comment, viewModel)
                },
                addProduct = { name, price, comment ->
                    productsState.value.addProduct(name, price, comment, viewModel)
                },
                refreshStockContent = { this.refresh(viewModel) },
                retryStockContent = { this.retry(viewModel) },
                retryProductContent = { this.retry(viewModel) },
                refreshProductContent = { this.refresh(viewModel) }
            )
        }

        composable<Navigation.Product> {
            val destination: Navigation.Product = it.toRoute()

            ProductScreen(
                viewModel = state.scopedContainer.productViewModel(destination.productId),
                formatter = state.singletonContainer.formatter
            ) { categoryId -> state.scopedContainer.warehouseViewModel(categoryId).refresh() }
        }
    }
}