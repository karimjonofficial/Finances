package com.orka.warehouse

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.orka.components.EmptyScreen
import com.orka.components.InitialScreen
import com.orka.components.InitializingScreen
import com.orka.components.OfflineScreen
import com.orka.core.Formatter
import com.orka.products.ProductsScreenState
import com.orka.products.ProductsScreenViewModel
import com.orka.warehouse.parts.ProductsList

@Composable
fun ProductsContent(
    modifier: Modifier = Modifier,
    viewModel: ProductsScreenViewModel,
    lazyListState: LazyListState = rememberLazyListState(),
    formatter: Formatter
) {
    val uiState = viewModel.uiState.collectAsState()

    when(val state = uiState.value) {

        ProductsScreenState.Initial -> {
            InitialScreen()
            viewModel.fetch()
        }

        is ProductsScreenState.Initialized -> {
            ProductsList(
                modifier = modifier,
                map = state.productsMap,
                state = lazyListState,
                formatter = formatter,
                selectProduct = { viewModel.select(it) }
            )
        }

        ProductsScreenState.Empty -> { EmptyScreen() }
        ProductsScreenState.Initializing -> { InitializingScreen() }
        ProductsScreenState.Offline -> { OfflineScreen() }
    }
}