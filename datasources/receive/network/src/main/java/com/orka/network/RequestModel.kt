package com.orka.network

import com.orka.receive.ReceiveItem
import kotlinx.serialization.Serializable

@Serializable
internal data class RequestModel(
    val items: List<ReceiveItem>,
    val price: String,
    val comment: String
)