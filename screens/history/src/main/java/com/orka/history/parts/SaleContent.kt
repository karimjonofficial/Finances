package com.orka.history.parts

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import com.orka.components.EmptyScreen
import com.orka.components.InitialScreen
import com.orka.components.InitializingScreen
import com.orka.components.OfflineScreen
import com.orka.core.Formatter
import com.orka.history.HistoryScreenViewModel
import com.orka.history.SaleContentState

@Composable
internal fun SaleContent(
    state: SaleContentState,
    viewModel: HistoryScreenViewModel,
    saleListState: LazyListState,
    formatter: Formatter
) {
    when (state) {

        SaleContentState.Initial -> {
            InitialScreen()
            viewModel.fetch()
        }

        is SaleContentState.Initialized -> {
            SaleHistoryList(
                map = state.categories,
                lazyListState = saleListState,
                formatter = formatter
            )
        }

        SaleContentState.Empty -> {
            EmptyScreen()
        }

        SaleContentState.Initializing -> {
            InitializingScreen()
        }

        SaleContentState.Offline -> {
            OfflineScreen()
        }
    }
}