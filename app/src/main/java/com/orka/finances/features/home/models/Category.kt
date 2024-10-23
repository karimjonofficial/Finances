package com.orka.finances.features.home.models

import androidx.annotation.DrawableRes
import kotlinx.serialization.Serializable

@Serializable
data class Category(
    val id: Int,
    val name: String,
    @DrawableRes val iconRes: Int,
    val description: String = "",
)
