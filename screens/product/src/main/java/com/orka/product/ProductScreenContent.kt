package com.orka.product

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.orka.components.InitialScreen
import com.orka.components.InitializingScreen
import com.orka.core.Formatter
import com.orka.product.parts.EditContent
import com.orka.product.parts.InitializedContent

@Composable
internal fun ProductScreenContent(
    modifier: Modifier = Modifier,
    viewModel: ProductScreenViewModel,
    formatter: Formatter,
    reloadWarehouse: (Int) -> Unit
) {
    val uiState = viewModel.uiState.collectAsState()

    when(val state = uiState.value) {

        is ProductScreenStates.Initial -> {
            InitialScreen(modifier = modifier.fillMaxSize())
            viewModel.handle(ProductScreenEvents.Init)
        }

        is ProductScreenStates.Processing -> {
            InitializingScreen(modifier = modifier.fillMaxSize())
            viewModel.handle(ProductScreenEvents.Process)
        }

        is ProductScreenStates.Initialized -> {
            InitializedContent(modifier.fillMaxSize(), state) { viewModel.handle(ProductScreenEvents.Edit) }
        }

        is ProductScreenStates.Editing -> {
            EditContent(state, modifier.fillMaxSize(), formatter) { name, price, description ->
                viewModel.handle(ProductScreenEvents.Save(name, price, description, reloadWarehouse))
            }
        }
    }
}