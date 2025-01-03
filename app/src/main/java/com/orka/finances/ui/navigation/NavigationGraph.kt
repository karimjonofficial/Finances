package com.orka.finances.ui.navigation

import android.graphics.Bitmap
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.layer.drawLayer
import androidx.compose.ui.graphics.rememberGraphicsLayer
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.orka.basket.BasketScreen
import com.orka.components.VerticalSpacer
import com.orka.core.Printer
import com.orka.history.HistoryScreen
import com.orka.home.HomeScreen
import com.orka.main.MainStates
import com.orka.product.ProductScreen
import com.orka.warehouse.WarehouseScreen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
internal fun NavigationGraph(
    modifier: Modifier = Modifier,
    state: MainStates.WithSingleton.WithCredential.WithContainers,
    navController: NavHostController,
    printer: Printer
) {
    val coroutineScope = rememberCoroutineScope()

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
                scan = { navController.navigate(Navigation.Check) }
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

        composable<Navigation.Check> {
            val graphicsLayer = rememberGraphicsLayer()

            Column(
                modifier = Modifier
                    .safeDrawingPadding()
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .drawWithContent {
                            graphicsLayer.record {
                                this@drawWithContent.drawContent()
                            }
                            drawLayer(graphicsLayer)
                        },
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    for (i in 1..10) {
                        Text(text = i.toString())
                    }
                }

                VerticalSpacer(16)

                Button(
                    onClick = {
                        coroutineScope.launch(Dispatchers.Main) {
                            printer.print(
                                bitmap = graphicsLayer
                                    .toImageBitmap()
                                    .asAndroidBitmap()
                                    .copy(Bitmap.Config.ARGB_8888, false)
                            )
                        }
                    }
                ) {
                    Text("Print")
                }
            }
        }
    }
}