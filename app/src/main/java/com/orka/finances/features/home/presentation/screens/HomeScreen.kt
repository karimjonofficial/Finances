package com.orka.finances.features.home.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.orka.finances.R
import com.orka.finances.features.home.data.sources.local.InMemoryCategoriesDataSource
import com.orka.finances.features.home.presentation.screens.parts.CategoriesList
import com.orka.finances.features.home.presentation.screens.parts.HomeScreenFloatingActionButton
import com.orka.finances.features.home.presentation.screens.parts.HomeScreenTopBar
import com.orka.finances.features.home.presentation.viewmodels.HomeScreenViewModel
import com.orka.finances.lib.components.VerticalSpacer

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeScreenViewModel
) {
    val focusManager = LocalFocusManager.current

    DisposableEffect(Unit) {
        focusManager.clearFocus()
        onDispose { }
    }

    Column(modifier = modifier) {

        val categories = viewModel.categories.collectAsState()
        val searchText = rememberSaveable { mutableStateOf("") }
        val focusRequester = remember { FocusRequester() }

        VerticalSpacer(16)

        OutlinedTextField(
            modifier = Modifier
                .height(56.dp)
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .focusRequester(focusRequester),
            value = searchText.value,
            singleLine = true,
            onValueChange = { searchText.value = it },
            placeholder = { Text(stringResource(R.string.search)) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Search",
                    tint = Color(0xFFBDBDBD)
                )
            },
            trailingIcon = {
                IconButton(onClick = {}) {
                    Icon(
                        painterResource(R.drawable.tune),
                        contentDescription = "Tune"
                    )
                }
            },
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color(0xFFF5F5F5),
                unfocusedContainerColor = Color(0xFFF5F5F5),
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = Color.Transparent,
            )
        )

        VerticalSpacer(16)

        CategoriesList(
            modifier = Modifier.padding(horizontal = 32.dp),
            categories = categories.value,
            categoryClick = { viewModel.selectCategory(it) }
        )
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
private fun HomeScreenPreview() {
    val dataSource = InMemoryCategoriesDataSource()
    dataSource.loadInitialData()
    val viewModel = HomeScreenViewModel(dataSource) {}

    Scaffold(
        topBar = { HomeScreenTopBar() },
        floatingActionButton = { HomeScreenFloatingActionButton() }
    ) {
        HomeScreen(Modifier.padding(it), viewModel)
    }
}