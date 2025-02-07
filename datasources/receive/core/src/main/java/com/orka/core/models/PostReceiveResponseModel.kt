package com.orka.core.models

import kotlinx.datetime.Instant
import kotlinx.datetime.serializers.InstantIso8601Serializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostReceiveResponseModel(
    @SerialName("id") val id: Int,

    @SerialName("datetime")
    @Serializable(with = InstantIso8601Serializer::class)
    val dateTime: Instant,

    @SerialName("price") val price: Double,
    @SerialName("comment") val comment: String
)

