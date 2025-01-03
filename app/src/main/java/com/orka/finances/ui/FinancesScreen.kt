package com.orka.finances.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.orka.core.Printer
import com.orka.finances.ui.navigation.NavigationGraph
import com.orka.finances.ui.navigation.navigateToProduct
import com.orka.finances.ui.navigation.navigateToWarehouse
import com.orka.login.LoginScreen
import com.orka.main.MainEvent
import com.orka.main.MainStates
import com.orka.main.MainViewModel

@Composable
fun FinancesScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel,
    printer: Printer
) {
    val mainState = viewModel.uiState.collectAsState()

    when (val state = mainState.value) {

        MainStates.Initial -> {
            InitialScreen(modifier = modifier) {
                viewModel.handle(MainEvent.Initialize)
            }
        }

        is MainStates.WithSingleton -> {
            val navController = rememberNavController()

            when (state) {

                is MainStates.WithSingleton.UnAuthorized -> {
                    LoginScreen(modifier, state.singletonContainer.loginScreenViewModel)
                }

                is MainStates.WithSingleton.WithCredential.Initializing -> {
                    LoadingScreen(modifier = modifier) {
                        viewModel.handle(MainEvent.InitContainers(
                            navigateToWarehouse = { navController.navigateToWarehouse(it) },
                            navigateToStockProduct = { navController.navigateToProduct(it) }
                        ))
                    }
                }

                is MainStates.WithSingleton.WithCredential.WithContainers -> {
                    NavigationGraph(modifier, state, navController, printer)
                }
            }
        }
    }
}

