package com.orka.finances.features.products.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Product(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("price") val price: Double,
    @SerialName("amount") val amount: Int,
    @SerialName("img_src") val imgSrc: String
)
