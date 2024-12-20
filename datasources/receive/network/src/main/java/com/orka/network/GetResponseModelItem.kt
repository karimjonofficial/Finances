package com.orka.network

import com.orka.products.Product
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class GetResponseModelItem(
    @SerialName("id") val id: Int,
    @SerialName("product") val product: Product,
    @SerialName("quantity") val amount: Int
)