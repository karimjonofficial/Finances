package com.orka.stock.parts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.orka.stock.StockItem
import com.orka.stock.components.StockItemCard
import com.orka.ui.VerticalSpacer

@Composable
fun StockItemsList(
    modifier: Modifier,
    items: List<StockItem>,
    select: (StockItem) -> Unit
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
                stockItem = item
            ) { select(item) }
        }
    }
}