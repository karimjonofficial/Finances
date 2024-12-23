package com.orka.core.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostSaleRequestModelItem(
    @SerialName("amount") val amount: Int,
    @SerialName("product") val productId: Int
)