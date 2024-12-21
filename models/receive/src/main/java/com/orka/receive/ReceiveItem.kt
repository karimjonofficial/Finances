package com.orka.receive

import com.orka.products.Product
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReceiveItem(
    @SerialName("id") val id: Int,
    @SerialName("product") val product: Product,
    @SerialName("quantity") val amount: Int
)