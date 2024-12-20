package com.orka.stock

import com.orka.products.Product
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StockItem(
    @SerialName("id") val id: Int,
    @SerialName("product") val product: Product,
    @SerialName("quantity") val amount: Int
)