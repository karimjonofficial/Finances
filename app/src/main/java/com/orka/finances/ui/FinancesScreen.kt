package com.orka.finances.ui

import android.icu.text.DecimalFormat
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.carousel.HorizontalMultiBrowseCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.orka.basket.BasketScreen
import com.orka.di.AppContainer
import com.orka.finances.ui.navigation.Navigation
import com.orka.finances.ui.navigation.navigateToBasket
import com.orka.finances.ui.navigation.navigateToHistory
import com.orka.finances.ui.navigation.navigateToHome
import com.orka.finances.ui.navigation.navigateToStockItem
import com.orka.finances.ui.navigation.navigateToWarehouse
import com.orka.history.HistoryScreen
import com.orka.home.HomeScreen
import com.orka.login.LoginScreen
import com.orka.main.AuthenticationState
import com.orka.main.MainViewModel
import com.orka.res.Drawables
import com.orka.res.Strings
import com.orka.warehouse.WarehouseScreen

@OptIn(ExperimentalMaterial3Api::class)
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

                    HomeScreen(
                        viewModel = container.homeScreenViewModel(authState.credential) {
                            navController.navigateToWarehouse(it)
                        },
                        navigateToHistory = { navController.navigateToHistory() },
                        navigateToBasket = { navController.navigateToBasket() }
                    )
                }

                composable<Navigation.Basket> {

                    BasketScreen(
                        viewModel = container.basketViewModel(),
                        navigateToHome = { navController.navigateToHome() },
                        navigateToHistory = { navController.navigateToHistory() },
                    )
                }

                composable<Navigation.History> {

                    HistoryScreen(
                        viewModel = container.historyViewModel(authState.credential),
                        navigateToHome = { navController.navigateToHome() },
                        navigateToBasket = { navController.navigateToBasket() },
                        formatCurrency = formatCurrency
                    )
                }

                composable<Navigation.Warehouse> {

                    val destination: Navigation.Warehouse = it.toRoute()

                    WarehouseScreen(
                        stockScreenViewModel = container.stockScreenViewModel(
                            credential = authState.credential,
                            categoryId = destination.categoryId,
                            navigate = { id -> navController.navigateToStockItem(id) }
                        ),
                        productsScreenViewModel = container.productsViewModel(
                            credential = authState.credential,
                            categoryId = destination.categoryId,
                        ),
                        formatCurrency = formatCurrency
                    )
                }

                composable<Navigation.StockItem> {

                    data class CarouselItem(
                        val id: Int,
                        @DrawableRes val imgRes: Int,
                        @StringRes val descriptionRes: Int
                    )

                    val carouselItems = listOf(
                        CarouselItem(1, Drawables.furniture1, Strings.furniture),
                        CarouselItem(2, Drawables.furniture1, Strings.furniture),
                        CarouselItem(3, Drawables.furniture1, Strings.furniture),
                        CarouselItem(4, Drawables.furniture1, Strings.furniture),
                        CarouselItem(5, Drawables.furniture1, Strings.furniture),
                        CarouselItem(6, Drawables.furniture1, Strings.furniture),
                    )

                    Scaffold(topBar = { TopAppBar(title = { Text("Stock items") }) }) { innerPadding ->

                        Column(modifier = Modifier.padding(innerPadding)) {

                            HorizontalMultiBrowseCarousel(
                                state = rememberCarouselState { carouselItems.size },
                                modifier = Modifier.height(300.dp),
                                preferredItemWidth = 186.dp,
                                itemSpacing = 8.dp,
                                contentPadding = PaddingValues(16.dp)
                            ) { index ->

                                Image(
                                    modifier = Modifier
                                        .height(205.dp)
                                        .maskClip(MaterialTheme.shapes.extraLarge)
                                        .background(MaterialTheme.colorScheme.surfaceVariant),
                                    painter = painterResource(carouselItems[index].imgRes),
                                    contentDescription = stringResource(carouselItems[index].descriptionRes),
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}