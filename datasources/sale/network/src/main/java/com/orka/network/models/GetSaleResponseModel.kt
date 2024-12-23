package com.orka.network.models

import kotlinx.datetime.Instant
import kotlinx.datetime.serializers.InstantIso8601Serializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetSaleResponseModel(
    @SerialName("id") val id: Int,
    @SerialName("items") val items: List<GetSaleResponseModelItem>,
    @SerialName("price") val price: Double,

    @Serializable(InstantIso8601Serializer::class)
    @SerialName("datetime") val datetime: Instant,

    @SerialName("comment") val comment: String,
    @SerialName("user") val userId: Int
)
