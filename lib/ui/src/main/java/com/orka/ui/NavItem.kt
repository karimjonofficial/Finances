package com.orka.ui

import androidx.compose.ui.graphics.vector.ImageVector

data class NavItem(
    val name: String,
    val selected: Boolean,
    val description: String,
    val icon: ImageVector,
    val click: () -> Unit
)