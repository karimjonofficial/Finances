package com.orka.finances.features.home.presentation.components

import com.orka.finances.R

fun convertToImage(iconName: String): Int? {
    return when(iconName) {
        "chair" -> R.drawable.chair
        else -> null
    }
}