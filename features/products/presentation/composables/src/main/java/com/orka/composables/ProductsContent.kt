package com.orka.composables

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.orka.viewmodels.ProductsScreenViewModel

@Composable
internal fun ProductsContent(
    modifier: Modifier = Modifier,
    viewModel: ProductsScreenViewModel,
    lazyListState: LazyListState = rememberLazyListState()
) {
    val uiState = viewModel.uiState.collectAsState()

    ProductsList(
        modifier = modifier,
        items = uiState.value,
        state = lazyListState
    )
}