package com.orka.finances.features.login.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserCredentials(
    @SerialName("access") val access: String,
    @SerialName("refresh") val refresh: String
)
