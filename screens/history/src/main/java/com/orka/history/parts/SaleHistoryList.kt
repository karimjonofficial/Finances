package com.orka.history.parts

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.orka.components.VerticalSpacer
import com.orka.core.Formatter
import com.orka.history.components.DateHeader
import com.orka.history.components.SaleCard
import com.orka.sale.Sale
import kotlinx.datetime.LocalDate

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun SaleHistoryList(
    modifier: Modifier = Modifier,
    map: Map<LocalDate, List<Sale>>,
    state: LazyListState,
    formatter: Formatter,
) {
    LazyColumn(modifier = modifier.fillMaxSize(), state = state) {

        item { VerticalSpacer(16) }

        map.forEach {

            stickyHeader { DateHeader(formatter, it.key) }

            items(it.value) { item ->
                SaleCard(item = item, formatter = formatter)
            }
        }
    }
}

