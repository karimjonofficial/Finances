package com.orka.finances

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.orka.finances.features.home.presentation.screens.HomeScreen
import com.orka.finances.features.home.presentation.screens.parts.HomeScreenFloatingActionButton
import com.orka.finances.features.home.presentation.screens.parts.HomeScreenTopBar
import com.orka.finances.features.home.presentation.viewmodels.HomeScreenViewModel
import com.orka.finances.features.login.presentation.screens.LoginScreen
import com.orka.finances.features.login.presentation.viewmodel.LoginScreenViewModel
import com.orka.finances.lib.data.UserCredentials
import com.orka.finances.lib.log.Log
import com.orka.finances.lib.log.formatTag
import com.orka.finances.ui.navigation.Navigation

@Composable
fun FinancesAppScreen(
    modifier: Modifier = Modifier,
    container: AppContainer
) {
    val navController = rememberNavController()
    val loaded = rememberSaveable { mutableStateOf(false) }

    if(!loaded.value) {
        LaunchedEffect(Unit) {
            container.initialize()
            loaded.value = true
        }
    } else {
        //TODO These messes should be contained in view model or smth else
        //TODO Don't forget writing tests for this logic

        var startDestination: Navigation = Navigation.Login
        container.credentialsDataSource?.let {
            startDestination = Navigation.Home
        }

        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = modifier
        ) {
            composable<Navigation.Login> {
                AppScaffold(modifier = modifier) { innerPadding ->
                    val loginDataSource = container.loginDataSource

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
                    val homeScreenViewModel = container.getHomeScreenViewModel(
                        credentialsDataSource = container.credentialsDataSource
                    ) { navController.navigate(Navigation.Products(it)) }

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
    //TODO view models are not kept through recomposition
    //TODO view models are created two times every change of destination
}

private fun log(tag: String, message: String) {
    Log(formatTag("FinancesScreen", tag), message)
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