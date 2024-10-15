package com.orka.finances.lib.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun VerticalSpacer(height: Int, modifier: Modifier = Modifier) {
    Spacer(modifier = modifier.height(height.dp))
}

@Composable
fun HorizontalSpacer(width: Int, modifier: Modifier = Modifier) {
    Spacer(modifier = modifier.width(width.dp))
}