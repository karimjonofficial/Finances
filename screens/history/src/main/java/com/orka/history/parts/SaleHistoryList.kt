package com.orka.history.parts

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.orka.core.Formatter
import com.orka.history.components.SaleCard
import com.orka.sale.Sale
import com.orka.ui.VerticalSpacer

@Composable
internal fun SaleHistoryList(
    modifier: Modifier = Modifier,
    items: List<Sale>,
    state: LazyListState,
    formatter: Formatter
) {

    LazyColumn(modifier = modifier.fillMaxSize(), state = state) {

        item { VerticalSpacer(16) }
        items(items) { SaleCard(item = it, formatter = formatter) }
    }
}