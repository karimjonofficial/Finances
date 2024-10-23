package com.orka.finances.features.home.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CategoryModel(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("icon") val iconName: String = "",
    @SerialName("description") val description: String = "",
)
