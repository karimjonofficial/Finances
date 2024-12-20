package com.orka.receive

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Receive(
    @SerialName("id") val id: Int,
    @SerialName("items") val items: List<ReceiveItem>,
    @SerialName("price") val price: Double,
    @SerialName("user_id") val userId: Int
)