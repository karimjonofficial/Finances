package com.orka.items

import androidx.compose.ui.graphics.painter.Painter

data class NavItem(
    val name: String,
    val selected: Boolean,
    val description: String,
    val painter: Painter,
    val click: () -> Unit
)