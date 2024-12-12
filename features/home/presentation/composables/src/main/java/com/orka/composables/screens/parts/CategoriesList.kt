package com.orka.composables.screens.parts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.orka.base.Category
import com.orka.composables.R
import com.orka.composables.components.CategoryButton

@Composable
internal fun CategoriesList(
    modifier: Modifier = Modifier,
    categories: List<Category>,
    categoryClick: (Category) -> Unit
) {
    LazyVerticalGrid(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(32.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        columns = GridCells.Fixed(2)
    ) {
        items(items = categories, key = { it.id }) { it ->
            CategoryButton(category = it, click = { categoryClick(it) })
        }
    }
}

@Preview
@Composable
private fun CategoriesListPreview() {
    val categoriesList = listOf(
        Category(1, stringResource(R.string.furniture)),
        Category(2, "Stol"),
        Category(3, stringResource(R.string.table)),
    )

    Box(Modifier.fillMaxSize().background(Color.White).padding(24.dp)) {
        CategoriesList(
            modifier = Modifier.fillMaxSize(),
            categories = categoriesList,
            categoryClick = {}
        )
    }
}