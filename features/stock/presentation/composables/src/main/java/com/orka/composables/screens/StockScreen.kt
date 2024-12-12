package com.orka.composables.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.orka.composables.data.InMemoryStockDataSource
import com.orka.viewmodels.StockScreenViewModel

@Composable
fun StockScreen(
    modifier: Modifier = Modifier,
    viewModel: StockScreenViewModel
) {
    viewModel.fetchData()

    StockContent(
        modifier = modifier,
        viewModel = viewModel
    )
}

@Preview
@Composable
private fun ProductsScreenPreview() {
    val categoryId = 1
    val productsDataSource = InMemoryStockDataSource()
    val viewModel = StockScreenViewModel(categoryId, productsDataSource, {}) {}
    viewModel.fetchData()

    Scaffold { innerPadding ->
        StockContent(
            modifier = Modifier.padding(innerPadding),
            viewModel = viewModel
        )
    }
}