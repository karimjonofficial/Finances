package com.orka.finances.lib.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Spacer16(modifier: Modifier = Modifier, times: Int = 1) {
    Spacer(modifier = modifier.padding((16 * times).dp))
}

@Composable
fun Spacer8(modifier: Modifier = Modifier, times: Int = 1) {
    Spacer(modifier = modifier.padding((8 * times).dp))
}