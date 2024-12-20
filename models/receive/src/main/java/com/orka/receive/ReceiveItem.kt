package com.orka.receive

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReceiveItem(
    @SerialName("product") val productId: Int,
    @SerialName("quantity") val amount: Int
)