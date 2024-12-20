package com.orka.home.parts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.orka.categories.Category
import com.orka.home.components.CategoryButton
import com.orka.res.Strings

@Composable
internal fun CategoriesList(
    modifier: Modifier = Modifier,
    categories: List<Category>,
    categoryClick: (Category) -> Unit,
    state: LazyGridState = rememberLazyGridState()
) {
    LazyVerticalGrid(
        modifier = modifier,
        state = state,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        contentPadding = PaddingValues(horizontal = 24.dp),
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
        Category(1, stringResource(Strings.furniture)),
        Category(2, stringResource(Strings.table))
    )

    Box(Modifier.fillMaxSize().background(Color.White).padding(24.dp)) {
        CategoriesList(
            modifier = Modifier.fillMaxSize(),
            categories = categoriesList,
            categoryClick = {}
        )
    }
}