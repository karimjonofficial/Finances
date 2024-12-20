package com.orka.stock

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.orka.stock.parts.StockItemsList

@Composable
fun StockContent(
    modifier: Modifier = Modifier,
    viewModel: StockScreenViewModel
) {
    val uiState = viewModel.uiState.collectAsState()

    StockItemsList(modifier, uiState.value) {
        viewModel.select(it)
    }
}