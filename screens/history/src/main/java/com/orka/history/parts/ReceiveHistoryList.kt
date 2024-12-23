package com.orka.history.parts

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.orka.core.Formatter
import com.orka.history.components.ReceiveCard
import com.orka.receive.Receive
import com.orka.ui.VerticalSpacer
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun ReceiveHistoryList(
    modifier: Modifier = Modifier,
    items: List<Receive>,
    state: LazyListState,
    formatter: Formatter
) {
    val grouped = items.reversed().groupBy { it.datetime.toLocalDateTime(TimeZone.currentSystemDefault()).date }

    LazyColumn(modifier = modifier.fillMaxSize(), state = state) {

        item { VerticalSpacer(16) }

        grouped.forEach {
            stickyHeader {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surfaceDim),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = formatter.formatDate(it.key), fontWeight = FontWeight.Bold)
                }
            }
            items(it.value) { item -> ReceiveCard(item = item, formatter = formatter) }
        }
    }
}