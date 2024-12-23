package com.orka.network.models

import com.orka.products.Product
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetSaleResponseModelItem(
    @SerialName("id") val id: Int,
    @SerialName("product") val product: Product,
    @SerialName("amount") val amount: Double,
    @SerialName("sale") val saleId: Int
)
