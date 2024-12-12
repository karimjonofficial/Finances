package com.orka.composables.screens

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import com.orka.composables.screens.parts.AddCategoryDialog
import com.orka.composables.screens.parts.HomeScreenFloatingActionButton
import com.orka.composables.screens.parts.HomeScreenTopBar
import com.orka.lib.ui.AppScaffold
import com.orka.viewmodels.HomeScreenViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeScreenViewModel,
    unauthorize: () -> Unit
) {
    val dialogVisible = rememberSaveable { mutableStateOf(false) }

    AppScaffold(
        topBar = { HomeScreenTopBar { unauthorize() } },
        floatingActionButton = {
            HomeScreenFloatingActionButton { dialogVisible.value = true }
        },
        modifier = modifier,
    ) { innerPadding ->

        if (dialogVisible.value) {
            AddCategoryDialog(
                modifier = Modifier.fillMaxWidth(),
                dismissRequest = { dialogVisible.value = false },
                addClick = { name, description ->
                    viewModel.addCategory(name, description)
                    dialogVisible.value = false
                }
            )
        }

        HomeContent(
            modifier = Modifier.padding(innerPadding),
            viewModel = viewModel
        )
    }
}