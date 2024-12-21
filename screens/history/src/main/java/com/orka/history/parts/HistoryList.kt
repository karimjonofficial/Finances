package com.orka.history.parts

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.orka.history.components.ReceiveCard
import com.orka.receive.Receive
import com.orka.ui.VerticalSpacer

@Composable
internal fun HistoryList(
    modifier: Modifier = Modifier,
    items: List<Receive>,
    state: LazyListState,
    formatCurrency: (Double) -> String
) {

    LazyColumn(modifier = modifier.fillMaxSize(), state = state) {
        item { VerticalSpacer(16) }
        items(items) { ReceiveCard(item = it, formatCurrency = formatCurrency) }
    }
}