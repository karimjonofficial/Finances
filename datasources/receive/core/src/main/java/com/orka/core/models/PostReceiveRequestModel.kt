package com.orka.core.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostReceiveRequestModel(
    @SerialName("items") val items: List<PostReceiveRequestModelItem>,
    @SerialName("price") val price: String,
    @SerialName("comment") val comment: String
)