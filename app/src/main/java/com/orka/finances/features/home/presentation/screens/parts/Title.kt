package com.orka.finances.features.home.presentation.screens.parts

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
internal fun Title() {
    Text(
        text = "Categories",
        modifier = Modifier.padding(start = 16.dp)
    )
}