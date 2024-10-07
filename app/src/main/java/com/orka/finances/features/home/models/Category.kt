package com.orka.finances.features.home.models

import androidx.annotation.DrawableRes
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Category(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("icon_id") @DrawableRes val imageRes: Int,
    @SerialName("description") val description: String,
)
