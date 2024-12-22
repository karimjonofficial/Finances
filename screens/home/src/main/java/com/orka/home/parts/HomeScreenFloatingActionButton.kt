package com.orka.home.parts

import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.orka.res.Drawables
import com.orka.res.Strings

@Composable
internal fun HomeScreenFloatingActionButton(
    click: () -> Unit
) {
    FloatingActionButton(onClick = { click() }) {
        Icon(
            painter = painterResource(Drawables.add),
            contentDescription = stringResource(Strings.add)
        )
    }
}