package com.orka.receive

import kotlinx.datetime.Instant
import kotlinx.datetime.serializers.InstantIso8601Serializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Receive(
    @SerialName("id") val id: Int,
    @SerialName("items") val items: List<ReceiveItem>,

    @Serializable(with = InstantIso8601Serializer::class)
    @SerialName("datetime") val datetime: Instant,

    @SerialName("price") val price: Double,
    @SerialName("comment") val comment: String,
    @SerialName("user") val userId: Int
)