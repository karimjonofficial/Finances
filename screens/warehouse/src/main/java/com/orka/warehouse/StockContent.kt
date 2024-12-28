package com.orka.warehouse

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.orka.components.EmptyScreen
import com.orka.components.InitialScreen
import com.orka.components.InitializingScreen
import com.orka.components.OfflineScreen
import com.orka.core.Formatter
import com.orka.stock.StockScreenState
import com.orka.stock.StockScreenViewModel
import com.orka.warehouse.parts.StockItemsList

@Composable
fun StockContent(
    modifier: Modifier = Modifier,
    viewModel: StockScreenViewModel,
    formatter: Formatter
) {
    val uiState = viewModel.stockItemsState.collectAsState()

    when(val state = uiState.value) {
        StockScreenState.Initial -> {
            InitialScreen()
            viewModel.fetch()
        }

        is StockScreenState.Initialized -> {
            StockItemsList(
                modifier = modifier,
                map = state.stockItemsMap,
                formatter = formatter,
                select = { viewModel.select(it) }
            )
        }

        StockScreenState.Empty -> { EmptyScreen() }
        StockScreenState.Initializing -> { InitializingScreen() }
        StockScreenState.Offline -> { OfflineScreen() }
    }
}