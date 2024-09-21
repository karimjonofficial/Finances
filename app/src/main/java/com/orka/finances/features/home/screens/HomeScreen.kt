package com.orka.finances.features.home.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.orka.finances.features.home.data.models.CategoryModel
import com.orka.finances.features.home.viewmodels.HomeScreenViewModel

@Composable
fun HomeScreen(viewModel: HomeScreenViewModel) {
    Scaffold(
        topBar = { HomeScreenTopBar() },
        floatingActionButton = { HomeScreenFloatingActionButton() }
    ) { innerPadding ->
        val uiState = viewModel.uiState

        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            Text(
                text = "Categories",
                modifier = Modifier.padding(start = 16.dp)
            )

            LazyVerticalGrid(
                modifier = Modifier.padding(horizontal = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                columns = GridCells.Fixed(3)
            ) {
                items(uiState.value.categories) {
                    CategoryButton(it)
                }
            }
        }
    }
}

@Composable
fun HomeScreenFloatingActionButton() {
    FloatingActionButton(onClick = {}) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = "Add"
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenTopBar() {
    TopAppBar(
        title = { Text("MyApp") },
        actions = {
            IconButton(onClick = {}) {
                Icon(
                    imageVector = Icons.Outlined.Search,
                    contentDescription = "Search"
                )
            }
        }
    )
}

@Composable
fun CategoryButton(category: CategoryModel) {
    Card(
        modifier = Modifier
            .size(140.dp)
            .clickable {  }
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(category.name)
        }
    }
}
