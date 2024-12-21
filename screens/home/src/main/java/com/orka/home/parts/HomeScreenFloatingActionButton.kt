package com.orka.home.parts

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.orka.res.Strings

@Composable
internal fun HomeScreenFloatingActionButton(
    click: () -> Unit
) {
    FloatingActionButton(onClick = { click() }) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = stringResource(Strings.add)
        )
    }
}