package com.orka.product

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.orka.components.AppScaffold
import com.orka.core.Formatter

@Composable
fun ProductScreen(
    modifier: Modifier = Modifier,
    viewModel: ProductScreenViewModel,
    formatter: Formatter,
    reloadWarehouse: (Int) -> Unit
) {
    AppScaffold(modifier = modifier) { innerPadding ->

        ProductScreenContent(
            modifier = Modifier.padding(innerPadding),
            viewModel = viewModel,
            formatter = formatter,
            reloadWarehouse = reloadWarehouse
        )
    }
}