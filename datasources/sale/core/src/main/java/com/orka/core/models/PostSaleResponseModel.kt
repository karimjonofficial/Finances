package com.orka.core.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostSaleResponseModel(
    @SerialName("id") val id: Int,
    @SerialName("datetime") val datetime: String,
    @SerialName("price") val price: String,
    @SerialName("comment") val comment: String
) {

}