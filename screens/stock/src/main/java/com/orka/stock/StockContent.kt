package com.orka.stock

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.orka.core.Formatter
import com.orka.stock.parts.StockItemsList

@Composable
fun StockContent(
    modifier: Modifier = Modifier,
    viewModel: StockScreenViewModel,
    formatter: Formatter
) {

    viewModel.fetch()
    val uiState = viewModel.uiState.collectAsState()

    StockItemsList(
        modifier, uiState.value,
        select =  { viewModel.select(it) },
        addToBasket = { viewModel.addToBasket(it) },
        formatter = formatter
    )
}