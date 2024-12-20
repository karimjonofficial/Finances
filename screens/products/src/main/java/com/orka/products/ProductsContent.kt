package com.orka.products

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.orka.products.parts.ProductsList

@Composable
fun ProductsContent(
    modifier: Modifier = Modifier,
    viewModel: ProductsScreenViewModel,
    lazyListState: LazyListState = rememberLazyListState(),
    formatCurrency: (Double) -> String
) {
    val uiState = viewModel.uiState.collectAsState()

    ProductsList(
        modifier = modifier,
        items = uiState.value,
        state = lazyListState,
        formatCurrency = formatCurrency
    )
}