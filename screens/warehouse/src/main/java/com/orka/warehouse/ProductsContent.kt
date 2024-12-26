package com.orka.warehouse

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.orka.core.Formatter
import com.orka.products.ProductsScreenViewModel
import com.orka.warehouse.parts.ProductsList

@Composable
fun ProductsContent(
    modifier: Modifier = Modifier,
    viewModel: ProductsScreenViewModel,
    lazyListState: LazyListState = rememberLazyListState(),
    formatter: Formatter
) {

    viewModel.fetch()
    val uiState = viewModel.uiState.collectAsState()

    ProductsList(
        modifier = modifier,
        items = uiState.value,
        state = lazyListState,
        formatter = formatter
    )
}