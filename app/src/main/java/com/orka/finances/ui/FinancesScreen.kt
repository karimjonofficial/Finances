package com.orka.finances.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.orka.di.SingletonContainer
import com.orka.finances.ui.navigation.NavigationGraph
import com.orka.login.LoginScreen
import com.orka.main.AuthenticationState

@Composable
fun FinancesScreen(
    modifier: Modifier = Modifier,
    singletonContainer: SingletonContainer
) {
    val uiState = singletonContainer.mainViewModel.uiState.collectAsState()

    when (val authState = uiState.value) {

        AuthenticationState.Initial -> {
            SplashScreen(singletonContainer.mainViewModel)
        }

        AuthenticationState.UnAuthorized -> {
            LoginScreen(viewModel = singletonContainer.loginScreenViewModel)
        }

        is AuthenticationState.Authorized -> {
            NavigationGraph(modifier, singletonContainer, authState.credential)
        }
    }
}