package com.orka.basket.parts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.orka.basket.BasketScreenState
import com.orka.basket.BasketScreenViewModel
import com.orka.basket.components.BasketItemCard
import com.orka.core.Formatter
import com.orka.components.VerticalSpacer

@Composable
internal fun BasketItemsList(
    modifier: Modifier = Modifier,
    viewModel: BasketScreenViewModel,
    lazyListState: LazyListState,
    formatter: Formatter
) {

    val uiState = viewModel.uiState.collectAsState()

    when(val state = uiState.value) {

        is BasketScreenState.WithBasket -> {
            LazyColumn(
                modifier = modifier,
                contentPadding = PaddingValues(horizontal = 12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                state = lazyListState
            ) {

                item { VerticalSpacer(12) }

                items(state.basket.items) {
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

        BasketScreenState.Initial -> {}
        BasketScreenState.InProcess -> {
            Box(modifier = modifier)
        }
    }
}