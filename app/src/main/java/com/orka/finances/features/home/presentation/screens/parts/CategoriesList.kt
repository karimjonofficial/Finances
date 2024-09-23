package com.orka.finances.features.home.presentation.screens.parts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.orka.finances.features.home.presentation.components.CategoryButton

@Composable
internal fun CategoriesList(productsList: List<String>) {
    LazyVerticalGrid(
        modifier = Modifier.padding(horizontal = 24.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        columns = GridCells.Fixed(3)
    ) {
        items(productsList) {
            CategoryButton(it)
        }
    }
}