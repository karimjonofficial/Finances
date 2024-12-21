package com.orka.core.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostRequestModelItem(
    @SerialName("product") val productId: Int,
    @SerialName("quantity") val amount: Int
)
