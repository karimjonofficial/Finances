package com.orka.core.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostSaleRequestModel(

    @SerialName("items")
    val items: List<PostSaleRequestModelItem>,

    @SerialName("price")
    val price: String,

    @SerialName("comment")
    val comment: String
)

