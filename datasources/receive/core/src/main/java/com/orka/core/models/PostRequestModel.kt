package com.orka.core.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostRequestModel(
    @SerialName("items") val items: List<PostRequestModelItem>,
    @SerialName("price") val price: String,
    @SerialName("comment") val comment: String
)