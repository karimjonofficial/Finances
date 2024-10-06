package com.orka.finances.features.home.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.orka.finances.features.home.data.sources.local.CategoriesInMemoryDataSource
import com.orka.finances.features.home.presentation.screens.parts.HomeScreenFloatingActionButton
import com.orka.finances.features.home.presentation.screens.parts.HomeScreenTopBar
import com.orka.finances.features.home.presentation.viewmodels.HomeScreenViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeScreenViewModel
) {
    Column(modifier = modifier) {
        val productsList = viewModel.categories.collectAsState()
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
private fun HomeScreenPreview() {
    val dataSource = CategoriesInMemoryDataSource()
    val viewModel = HomeScreenViewModel(dataSource)

    Scaffold(
        topBar = { HomeScreenTopBar() },
        floatingActionButton = { HomeScreenFloatingActionButton() }
    ) {
        HomeScreen(Modifier.padding(it), viewModel)
    }
}