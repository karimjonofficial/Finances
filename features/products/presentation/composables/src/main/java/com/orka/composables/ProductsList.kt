package com.orka.composables

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.orka.base.Product

@Composable
internal fun ProductsList(
    modifier: Modifier,
    items: List<Product>,
    state: LazyListState
) {
    LazyColumn(
        modifier = modifier,
        state = state
    ) {
        items(items = items) {
            ProductCard(product = it)
        }
    }
}