package com.orka.history.parts

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.orka.receive.Receive

@Composable
internal fun HistoryScreenContent(
    modifier: Modifier = Modifier,
    items: List<Receive>,
    lazyListState: LazyListState,
    formatCurrency: (Double) -> String
) {
    HistoryList(
        modifier = modifier,
        items = items,
        state = lazyListState,
        formatCurrency = formatCurrency
    )
}