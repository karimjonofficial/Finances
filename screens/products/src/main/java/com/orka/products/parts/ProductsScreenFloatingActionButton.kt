package com.orka.products.parts

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable

@Composable
internal fun ProductsScreenFloatingActionButton(
    click: () -> Unit
) {
    FloatingActionButton(onClick = { click() }) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = "Add"
        )
    }
}