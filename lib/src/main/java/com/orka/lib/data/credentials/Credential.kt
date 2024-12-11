package com.orka.finances.lib.data.credentials

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Credential(
    @SerialName("access") val token: String,
    @SerialName("refresh") val refresh: String
)