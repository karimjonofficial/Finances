package com.orka.lib.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Credential(
    @SerialName("access") val token: String,
    @SerialName("refresh") val refresh: String
)