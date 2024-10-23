package com.orka.finances.features.home.presentation.screens.parts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.orka.finances.features.home.models.Category
import com.orka.finances.features.home.presentation.components.CategoryButton

@Composable
internal fun CategoriesList(
    modifier: Modifier = Modifier,
    categories: List<Category>,
    categoryClick: (Category) -> Unit
) {
    LazyVerticalGrid(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        columns = GridCells.Fixed(2)
    ) {
        items(items = categories, key = { it.id }) { it ->
            CategoryButton(category = it, click = { categoryClick(it) })
        }
    }
}