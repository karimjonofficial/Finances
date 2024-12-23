package com.orka.sale

import com.orka.products.Product
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SaleItem(
    @SerialName("id") val id: Int,
    @SerialName("product") val product: Product,
    @SerialName("amount") val amount: Int,
    @SerialName("sale") val saleId: Int
)
