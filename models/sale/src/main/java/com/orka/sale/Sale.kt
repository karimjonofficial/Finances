package com.orka.sale

import kotlinx.datetime.Instant
import kotlinx.datetime.serializers.InstantIso8601Serializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Sale(
    @SerialName("id") val id: Int,
    @SerialName("items") val items: List<SaleItem>,
    @SerialName("price") val price: Double,

    @Serializable(InstantIso8601Serializer::class)
    @SerialName("datetime") val datetime: Instant,

    @SerialName("comment") val comment: String,
    @SerialName("user") val userId: Int
)
