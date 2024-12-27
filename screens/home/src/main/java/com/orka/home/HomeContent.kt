package com.orka.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.orka.home.parts.CategoriesList

@Composable
internal fun HomeContent(
    modifier: Modifier = Modifier,
    viewModel: HomeScreenViewModel,
    lazyGridState: LazyGridState
) {
    val uiState = viewModel.uiState.collectAsState()

    CategoriesList(
        modifier = modifier.fillMaxSize(),
        state = lazyGridState,
        categories = uiState.value,
        categoryClick = { viewModel.select(it) }
    )
}