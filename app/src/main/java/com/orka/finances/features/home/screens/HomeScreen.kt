package com.orka.finances.features.home.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.orka.finances.features.home.components.CategoryButton
import com.orka.finances.features.home.viewmodels.HomeScreenViewModel
import com.orka.finances.lib.components.Spacer16
import com.orka.finances.lib.components.Spacer8

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeScreenViewModel
) {
    Column(modifier = modifier) {
        val text = rememberSaveable { mutableStateOf("") }
        val productsList = rememberSaveable { mutableStateOf(products.toList()) }

        Title()
        Spacer8()
        AddCategoryLayout(text, productsList)
        Spacer16()
        CategoriesList(productsList)
    }
}

@Composable
private fun Title() {
    Text(
        text = "Categories",
        modifier = Modifier.padding(start = 16.dp)
    )
}

@Composable
private fun AddCategoryLayout(
    text: MutableState<String>,
    productsList: MutableState<List<String>>
) {
    Row(Modifier.padding(horizontal = 16.dp)) {
        TextField(
            value = text.value,
            label = { Text("Enter the name") },
            onValueChange = { text.value = it },
            modifier = Modifier.weight(1f)
        )

        Spacer8()

        Button(
            onClick = {
                products.add(text.value)
                text.value = ""
                productsList.value = products.toList()
            }
        ) {
            Text("Add")
        }
    }
}

@Composable
private fun CategoriesList(productsList: MutableState<List<String>>) {
    LazyVerticalGrid(
        modifier = Modifier.padding(horizontal = 24.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        columns = GridCells.Fixed(3)
    ) {
        items(productsList.value) {
            CategoryButton(it)
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
        title = { Text("Finances") },
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

val products = mutableListOf(
    "Product1",
    "Product2",
    "Product3",
)

val longProducts = listOf(
    "Product1",
    "Product2",
    "Product3",
    "Product4",
    "Product5",
    "Product6",
    "Product7",
    "Product8",
    "Product9",
    "Product10",
    "Product11",
    "Product12",
    "Product13",
    "Product14",
    "Product15",
    "Product16",
    "Product17",
    "Product18",
    "Product19",
    "Product20",
    "Product21",
    "Product22",
    "Product23",
    "Product24",
    "Product25",
    "Product26",
    "Product27",
    "Product28",
    "Product29",
    "Product30",
    "Product31",
    "Product32",
    "Product33",
    "Product34",
    "Product35",
    "Product36"
)