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
import com.orka.products.Product
import com.orka.res.Strings
import com.orka.stock.StockItem

@Composable
@OptIn(ExperimentalMaterial3Api::class)
internal fun WarehouseScreenContent(
    modifier: Modifier,
    stockContentState: StockContentStates,
    productsContentState: ProductsContentStates,
    initializeProductsContent: ProductsContentStates.Initial.() -> Unit,
    initializeStockItemsContent: StockContentStates.Initial.() -> Unit,
    processProductsContent: ProductsContentStates.Processing.() -> Unit,
    processStockContent: StockContentStates.Processing.() -> Unit,
    refreshStockContent: StockContentStates.Empty.() -> Unit,
    retryStockContent: StockContentStates.Failure.() -> Unit,
    refreshProductContent: ProductsContentStates.Empty.() -> Unit,
    retryProductContent: ProductsContentStates.Failure.() -> Unit,
    selectProduct: ProductsContentStates.Success.(Product) -> Unit,
    addToBasket: StockContentStates.Success.(StockItem) -> Unit,
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
                    state = stockContentState,
                    initialize = initializeStockItemsContent,
                    process = processStockContent,
                    addToBasket = addToBasket,
                    refresh = refreshStockContent,
                    retry = retryStockContent,
                    formatter = formatter
                )
            }

            1 -> {
                ProductsContent(
                    state = productsContentState,
                    formatter = formatter,
                    initialize = initializeProductsContent,
                    process = processProductsContent,
                    selectProduct = selectProduct,
                    retry = retryProductContent,
                    refresh = refreshProductContent
                )
            }
        }
    }
}