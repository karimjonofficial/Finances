package com.orka.ui

import androidx.compose.ui.graphics.painter.Painter

data class NavItem(
    val name: String,
    val selected: Boolean,
    val description: String,
    val icon: Painter,
    val click: () -> Unit
)