package com.orka.finances.ui.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.orka.finances.features.home.screens.HomeScreen
import com.orka.finances.features.home.screens.HomeScreenFloatingActionButton
import com.orka.finances.features.home.screens.HomeScreenTopBar
import com.orka.finances.features.home.viewmodels.HomeScreenViewModel

@Composable
fun FinancesAppScreen(modifier: Modifier = Modifier) {
    AppScaffold(
        modifier = modifier,
        topBar = { HomeScreenTopBar() },
        floatingActionButton = { HomeScreenFloatingActionButton() }
    ) { innerPadding ->
        val homeScreenViewModel = HomeScreenViewModel()

        HomeScreen(
            modifier = Modifier.padding(innerPadding),
            viewModel = homeScreenViewModel
        )
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