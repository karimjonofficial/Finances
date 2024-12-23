package com.orka.warehouse

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.orka.core.Formatter
import com.orka.products.ProductsContent
import com.orka.products.ProductsScreenViewModel
import com.orka.res.Strings
import com.orka.stock.StockContent
import com.orka.stock.StockScreenViewModel

@Composable
@OptIn(ExperimentalMaterial3Api::class)
internal fun WarehouseScreenContent(
    modifier: Modifier,
    stockScreenViewModel: StockScreenViewModel,
    productsScreenViewModel: ProductsScreenViewModel,
    formatter: Formatter
) {
    val tabIndex = rememberSaveable { mutableIntStateOf(0) }

    Column(modifier = modifier) {

        PrimaryTabRow(
            selectedTabIndex = tabIndex.intValue,
        ) {
            Tab(
                selected = tabIndex.intValue == 0,
                onClick = { tabIndex.intValue = 0 },
                text = { Text(stringResource(Strings.warehouse)) }
            )

            Tab(
                selected = tabIndex.intValue == 1,
                onClick = { tabIndex.intValue = 1 },
                text = { Text(stringResource(Strings.products)) }
            )
        }

        when (tabIndex.intValue) {
            0 -> {
                StockContent(
                    viewModel = stockScreenViewModel,
                    formatter = formatter
                )
            }

            1 -> {
                ProductsContent(
                    viewModel = productsScreenViewModel,
                    formatter = formatter
                )
            }
        }
    }
}