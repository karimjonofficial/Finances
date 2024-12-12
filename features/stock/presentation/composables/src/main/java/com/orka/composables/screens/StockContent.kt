package com.orka.composables.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.orka.viewmodels.StockScreenViewModel

@Composable
internal fun StockContent(
    modifier: Modifier = Modifier,
    viewModel: StockScreenViewModel
) {
    val uiState = viewModel.uiState.collectAsState()
    StockItemsList(modifier, uiState.value)
}