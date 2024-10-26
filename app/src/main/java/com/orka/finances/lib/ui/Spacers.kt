package com.orka.finances.lib.ui

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun VerticalSpacer(height: Int, modifier: Modifier = Modifier) {
    Spacer(modifier = modifier.height(height.dp))
}

@Composable
fun HorizontalSpacer(width: Int, modifier: Modifier = Modifier) {
    Spacer(modifier = modifier.width(width.dp))
}