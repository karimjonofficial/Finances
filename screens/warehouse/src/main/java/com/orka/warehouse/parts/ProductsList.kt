package com.orka.warehouse.parts

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.orka.components.StickyHeader
import com.orka.components.VerticalSpacer
import com.orka.core.Formatter
import com.orka.products.Product
import com.orka.warehouse.components.ProductCard

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun ProductsList(
    modifier: Modifier,
    map: Map<Char, List<Product>>,
    state: LazyListState,
    formatter: Formatter,
    selectProduct: (Product) -> Unit
) {

    LazyColumn(
        modifier = modifier,
        state = state
    ) {

        item { VerticalSpacer(16) }

        map.forEach {
            stickyHeader {
                StickyHeader(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = it.key.toString(),
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            items(items = it.value) { item ->

                ProductCard(
                    item = item,
                    formatter = formatter,
                    selectProduct = selectProduct
                )
            }
        }
    }
}