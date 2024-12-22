package com.orka.basket.parts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.orka.basket.BasketScreenViewModel
import com.orka.basket.components.BasketItemCard
import com.orka.core.Formatter
import com.orka.ui.VerticalSpacer

@Composable
internal fun BasketItemsList(
    modifier: Modifier = Modifier,
    viewModel: BasketScreenViewModel,
    state: LazyListState,
    formatter: Formatter
) {

    val uiState = viewModel.uiState.collectAsState()

    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        state = state
    ) {

        item { VerticalSpacer(12) }

        items(uiState.value.basket.items) {
            BasketItemCard(
                item = it,
                increaseClick = { productId -> viewModel.increase(productId) },
                decreaseClick = { productId -> viewModel.decrease(productId) },
                removeClick = { productId -> viewModel.remove(productId) },
                formatter = formatter
            )
        }

        item { VerticalSpacer(12) }
    }
}