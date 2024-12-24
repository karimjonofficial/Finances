package com.orka.history

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.orka.core.Formatter
import com.orka.history.parts.ReceiveHistoryList
import com.orka.history.parts.SaleHistoryList
import com.orka.res.Strings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun HistoryScreenContent(
    modifier: Modifier = Modifier,
    viewModel: HistoryScreenViewModel,
    receiveListState: LazyListState,
    saleListState: LazyListState,
    formatter: Formatter
) {
    val tabIndex = rememberSaveable { mutableIntStateOf(0) }
    viewModel.fetch()

    Column(modifier = modifier) {

        PrimaryTabRow(
            selectedTabIndex = tabIndex.intValue,
        ) {
            Tab(
                selected = tabIndex.intValue == 0,
                onClick = { tabIndex.intValue = 0 },
                text = { Text(stringResource(Strings.sale)) }
            )

            Tab(
                selected = tabIndex.intValue == 1,
                onClick = { tabIndex.intValue = 1 },
                text = { Text(stringResource(Strings.receive)) }
            )
        }

        when (tabIndex.intValue) {

            0 -> {
                val uiState = viewModel.saleUiState.collectAsState()

                SaleHistoryList(
                    map = uiState.value,
                    state = saleListState,
                    formatter = formatter
                )
            }

            1 -> {
                val uiState = viewModel.uiState.collectAsState()

                ReceiveHistoryList(
                    map = uiState.value,
                    state = receiveListState,
                    formatter = formatter
                )
            }
        }
    }
}

