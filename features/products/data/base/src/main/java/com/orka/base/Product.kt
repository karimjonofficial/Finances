package com.orka.base

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Product(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("price") val price: Double,
    @SerialName("description") val description: String,
    @SerialName("category") val categoryId: Int
)