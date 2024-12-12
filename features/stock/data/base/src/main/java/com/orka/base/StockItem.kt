package com.orka.base

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StockItem(
    @SerialName("id") val id: Int,
    @SerialName("product") val product: Product,
    @SerialName("warehouse") val warehouseId: Int,
    @SerialName("quantity") val amount: Int
)
