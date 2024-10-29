package com.orka.finances.lib.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Credentials(
    @SerialName("access") val token: String,
    @SerialName("refresh") val refresh: String
)