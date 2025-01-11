package com.orka.warehouse

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.orka.components.EmptyScreen
import com.orka.components.InitialScreen
import com.orka.components.InitializingScreen
import com.orka.components.OfflineScreen
import com.orka.core.Formatter
import com.orka.products.Product
import com.orka.warehouse.parts.ProductsList

@Composable
fun ProductsContent(
    modifier: Modifier = Modifier,
    state: ProductsContentStates,
    initialize: ProductsContentStates.Initial.() -> Unit,
    process: ProductsContentStates.Processing.() -> Unit,
    selectProduct: ProductsContentStates.Success.(Product) -> Unit,
    refresh: ProductsContentStates.Empty.() -> Unit,
    retry: ProductsContentStates.Failure.() -> Unit,
    lazyListState: LazyListState = rememberLazyListState(),
    formatter: Formatter
) {

    when(state) {

        is ProductsContentStates.Initial -> {
            InitialScreen()
            state.initialize()
        }

        is ProductsContentStates.Success -> {
            ProductsList(
                modifier = modifier,
                map = state.productsMap,
                state = lazyListState,
                formatter = formatter,
                selectProduct = { state.selectProduct(it) }
            )
        }

        is ProductsContentStates.Empty -> {
            EmptyScreen { state.refresh() }
        }

        is ProductsContentStates.Processing -> {
            InitializingScreen(text = state.message)
            state.process()
        }

        is ProductsContentStates.Failure -> {
            OfflineScreen(text = state.message) {
                state.retry()
            }
        }
    }
}