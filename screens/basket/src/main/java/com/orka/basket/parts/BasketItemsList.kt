package com.orka.basket.parts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.orka.basket.BasketItem
import com.orka.basket.components.BasketItemCard
import com.orka.ui.VerticalSpacer

@Composable
internal fun BasketItemsList(
    modifier: Modifier = Modifier,
    items: List<BasketItem>,
    state: LazyListState
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        state = state
    ) {
        item { VerticalSpacer(12) }
        items(items) { BasketItemCard(item = it) }
        item { VerticalSpacer(12) }
    }
}