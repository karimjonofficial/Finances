package com.orka.composables.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.orka.base.StockItem
import com.orka.composables.components.StockItemCard

@Composable
fun StockItemsList(
    modifier: Modifier,
    items: List<StockItem>
) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(items = items) {
            StockItemCard(
                modifier = Modifier.size(height = 200.dp, width = 180.dp),
                stockItem = it
            ) {}
        }
    }
}