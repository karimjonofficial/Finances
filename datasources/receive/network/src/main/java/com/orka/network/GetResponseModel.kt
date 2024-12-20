package com.orka.network

import kotlinx.datetime.Instant
import kotlinx.datetime.serializers.InstantIso8601Serializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class GetResponseModel(
    @SerialName("id") val id: Int,
    @SerialName("items") val items: List<GetResponseModelItem>,

    @SerialName("datetime")
    @Serializable(with = InstantIso8601Serializer::class)
    val dateTime: Instant,

    @SerialName("price") val price: Double,
    @SerialName("comment") val comment: String,
    @SerialName("user") val userId: Int
)