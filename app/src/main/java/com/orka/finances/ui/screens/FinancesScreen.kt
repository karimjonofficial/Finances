package com.orka.finances.ui.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.orka.finances.features.home.data.sources.local.CategoriesInMemoryDataSource
import com.orka.finances.features.home.presentation.screens.HomeScreen
import com.orka.finances.features.home.presentation.screens.parts.HomeScreenFloatingActionButton
import com.orka.finances.features.home.presentation.screens.parts.HomeScreenTopBar
import com.orka.finances.features.home.presentation.viewmodels.HomeScreenViewModel
import com.orka.finances.features.login.screens.LoginScreen

@Composable
fun FinancesAppScreen(modifier: Modifier = Modifier) {
    AppScaffold(
        modifier = modifier,
//        topBar = { HomeScreenTopBar() },
//        floatingActionButton = { HomeScreenFloatingActionButton() }
    ) { innerPadding ->
//        val dataSource = CategoriesInMemoryDataSource()
//        dataSource.loadInitialData()
//        val homeScreenViewModel = HomeScreenViewModel(dataSource)
//
//        HomeScreen(
//            modifier = Modifier.padding(innerPadding),
//            viewModel = homeScreenViewModel
//        )
        LoginScreen(modifier = Modifier.padding(innerPadding).fillMaxSize())
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