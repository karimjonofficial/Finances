package com.orka.finances.ui.navigation

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class Navigation {
    @Serializable data object Home : Navigation()
    @Serializable data class Products(@SerialName("category_id") val categoryId: Int) : Navigation()
    @Serializable data class Stock(@SerialName("category_id") val categoryId: Int) : Navigation()
    @Serializable data class StockItem(@SerialName("item_id") val itemId: Int) : Navigation()
}