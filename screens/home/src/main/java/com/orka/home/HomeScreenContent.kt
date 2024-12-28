package com.orka.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.orka.components.EmptyScreen
import com.orka.components.InitialScreen
import com.orka.components.InitializingScreen
import com.orka.components.OfflineScreen
import com.orka.home.parts.CategoriesList

@Composable
internal fun HomeScreenContent(
    modifier: Modifier = Modifier,
    viewModel: HomeScreenViewModel,
    lazyGridState: LazyGridState,
    state: HomeScreenState
) {
    when (state) {

        HomeScreenState.Initial -> {
            InitialScreen()
            viewModel.fetch()
        }

        is HomeScreenState.Initialized -> {

            CategoriesList(
                modifier = modifier.fillMaxSize(),
                state = lazyGridState,
                categories = state.categories,
                categoryClick = { viewModel.select(it) }
            )
        }

        HomeScreenState.Empty -> { EmptyScreen() }
        HomeScreenState.Initializing -> { InitializingScreen() }
        HomeScreenState.Offline -> { OfflineScreen() }
    }
}