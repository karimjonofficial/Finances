package com.orka.finances.features.stock.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.orka.finances.features.stock.data.sources.local.InMemoryStockDataSource
import com.orka.finances.features.stock.models.StockItem
import com.orka.finances.features.stock.presentation.components.StockItemCard
import com.orka.finances.features.stock.presentation.viewmodels.StockScreenViewModel

@Composable
fun StockScreen(
    modifier: Modifier = Modifier,
    viewModel: StockScreenViewModel
) {
    val uiState = viewModel.uiState.collectAsState()
    StockItemsList(modifier, uiState.value)
}

@Composable
fun StockItemsList(
    modifier: Modifier,
    items: List<StockItem>
) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(items = items) {
            StockItemCard(
                modifier = Modifier.size(height = 200.dp, width = 180.dp),
                stockItem = it
            ) {}
        }
    }
}

@Preview
@Composable
private fun ProductsScreenPreview() {
    val categoryId = 1
    val productsDataSource = InMemoryStockDataSource()
    val viewModel = StockScreenViewModel(categoryId, productsDataSource, {}) {}
    viewModel.fetchData()

    Scaffold { innerPadding ->
        StockScreen(
            modifier = Modifier.padding(innerPadding),
            viewModel = viewModel
        )
    }
}