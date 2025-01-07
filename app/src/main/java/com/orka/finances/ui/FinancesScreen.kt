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
import com.orka.main.AppEvents
import com.orka.main.AppStates
import com.orka.main.AppManager

@Composable
fun FinancesScreen(
    modifier: Modifier = Modifier,
    viewModel: AppManager,
    printer: Printer
) {
    val mainState = viewModel.uiState.collectAsState()

    when (val state = mainState.value) {

        AppStates.Initial -> {
            InitialScreen(modifier = modifier) {
                viewModel.handle(AppEvents.Initialize)
            }
        }

        is AppStates.HasSingleton -> {
            val navController = rememberNavController()

            when (state) {

                is AppStates.HasSingleton.UnAuthorized -> {
                    LoginScreen(modifier, state.singletonContainer.loginScreenViewModel)
                }

                is AppStates.HasSingleton.HasCredential.CreatingContainers -> {
                    LoadingScreen(modifier = modifier) {
                        viewModel.handle(AppEvents.InitContainers(
                            navigateToWarehouse = { navController.navigateToWarehouse(it) },
                            navigateToStockProduct = { navController.navigateToProduct(it) }
                        ))
                    }
                }

                is AppStates.HasSingleton.HasCredential.HasContainers -> {
                    NavigationGraph(modifier, state, navController, { viewModel.handle(AppEvents.UnAuthorize) }, printer)
                }
            }
        }
    }
}

