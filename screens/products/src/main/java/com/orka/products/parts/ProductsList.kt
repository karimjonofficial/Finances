package com.orka.products.parts

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.orka.core.Formatter
import com.orka.products.Product
import com.orka.products.components.ProductCard
import com.orka.ui.VerticalSpacer

@Composable
internal fun ProductsList(
    modifier: Modifier,
    items: List<Product>,
    state: LazyListState,
    formatter: Formatter
) {

    LazyColumn(
        modifier = modifier,
        state = state
    ) {

        item { VerticalSpacer(16) }

        items(items = items) {
            ProductCard(product = it, formatter = formatter)
        }
    }
}