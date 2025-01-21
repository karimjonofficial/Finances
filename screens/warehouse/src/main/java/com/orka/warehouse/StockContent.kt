package com.orka.warehouse

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.orka.components.EmptyScreen
import com.orka.components.InitialScreen
import com.orka.components.InitializingScreen
import com.orka.components.OfflineScreen
import com.orka.core.Formatter
import com.orka.warehouse.parts.StockItemsList

@Composable
fun StockContent(
    modifier: Modifier = Modifier,
    state: StockContentStates,
    formatter: Formatter
) {

    when(state) {
        is StockContentStates.Initial -> {
            InitialScreen()
            state.initialize()
        }

        is StockContentStates.Success -> {
            StockItemsList(
                modifier = modifier,
                map = state.stockItemsMap,
                formatter = formatter,
                select = { state.addToBasket(it) }
            )
        }

        is StockContentStates.Empty -> {
            EmptyScreen { state.refresh() }
        }

        is StockContentStates.Processing -> {
            InitializingScreen(text = stringResource(state.messageRes))
            state.process()
        }

        is StockContentStates.Failure -> {
            OfflineScreen(text = state.message) {
                state.retry()
            }
        }
    }
}