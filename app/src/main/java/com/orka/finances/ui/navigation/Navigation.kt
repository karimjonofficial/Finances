package com.orka.finances.ui.navigation

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class Navigation {
    @Serializable data object Login : Navigation()
    @Serializable data object Home : Navigation()
    @Serializable data class Products(@SerialName("category_id") val categoryId: Int)
}