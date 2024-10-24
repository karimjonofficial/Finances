package com.orka.finances

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.orka.finances.features.home.data.sources.local.InMemoryCategoriesDataSource
import com.orka.finances.features.home.presentation.screens.HomeScreen
import com.orka.finances.features.home.presentation.screens.parts.HomeScreenFloatingActionButton
import com.orka.finances.features.home.presentation.screens.parts.HomeScreenTopBar
import com.orka.finances.features.home.presentation.viewmodels.HomeScreenViewModel
import com.orka.finances.features.login.presentation.screens.LoginScreen
import com.orka.finances.features.login.presentation.viewmodel.LoginScreenViewModel
import com.orka.finances.ui.navigation.Navigation

@Composable
fun FinancesAppScreen(
    modifier: Modifier = Modifier,
    appContainer: AppContainer
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Navigation.Login,
        modifier = modifier
    ) {
        composable<Navigation.Login> {
            AppScaffold(modifier = modifier) { innerPadding ->
                val loginDataSource = appContainer.loginDataSource

                val loginViewModel = LoginScreenViewModel(
                    dataSource = loginDataSource,
                    passScreen = {
                        navController.navigate(Navigation.Home) {
                            popUpTo(Navigation.Login) { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                )

                LoginScreen(
                    modifier = Modifier.padding(innerPadding),
                    viewModel = loginViewModel
                )
            }
        }

        composable<Navigation.Home> {
            AppScaffold(
                topBar = { HomeScreenTopBar() },
                floatingActionButton = { HomeScreenFloatingActionButton() },
                modifier = modifier,
            ) { innerPadding ->
                val dataSource = InMemoryCategoriesDataSource()
                dataSource.loadInitialData()

                val homeScreenViewModel = HomeScreenViewModel(
                    dataSource = dataSource,
                    passScreen = { navController.navigate(Navigation.Products(it)) }
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

@Composable
private fun AppScaffold(
    modifier: Modifier = Modifier,
    topBar: @Composable (() -> Unit)? = null,
    bottomBar: @Composable (() -> Unit)? = null,
    floatingActionButton: @Composable (() -> Unit)? = null,
    content: @Composable (PaddingValues) -> Unit,
) {
    Scaffold(
        topBar = topBar ?: {},
        bottomBar = bottomBar ?: {},
        floatingActionButton = floatingActionButton ?: {},
        modifier = modifier
    ) { innerPadding ->
        content(innerPadding)
    }
}