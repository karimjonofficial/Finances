package com.orka.warehouse.parts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.orka.components.VerticalSpacer
import com.orka.core.Formatter
import com.orka.products.Product
import com.orka.stock.StockItem
import com.orka.warehouse.components.StockItemCard

@Composable
fun StockItemsList(
    modifier: Modifier,
    items: List<StockItem>,
    select: (StockItem) -> Unit,
    addToBasket: (Product) -> Unit,
    formatter: Formatter
) {

    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        item { VerticalSpacer(16) }
        item { VerticalSpacer(16) }
        
        items(items = items) { item ->

            StockItemCard(
                modifier = Modifier.size(height = 300.dp, width = 200.dp),
                item = item,
                click = { select(item) },
                addClick = { addToBasket(it) },
                formatter = formatter
            )
        }
    }
}