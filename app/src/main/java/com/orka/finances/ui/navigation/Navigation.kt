package com.orka.finances.ui.navigation

import androidx.navigation.NavController
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class Navigation {
    @Serializable data object Home : Navigation()
    @Serializable data class StockItem(@SerialName("item_id") val itemId: Int) : Navigation()
    @Serializable data class Warehouse(@SerialName("category_id") val categoryId: Int) : Navigation()
    @Serializable data object History : Navigation()
    @Serializable data object Basket : Navigation()
}

fun NavController.navigateToHome() {
    this.navigate(Navigation.Home)
}

fun NavController.navigateToStockItem(itemId: Int) {
    this.navigate(Navigation.StockItem(itemId))
}

fun NavController.navigateToWarehouse(categoryId: Int) {
    this.navigate(Navigation.Warehouse(categoryId))
}

fun NavController.navigateToBasket() {
    this.navigate(Navigation.Basket)
}

fun NavController.navigateToHistory() {
    this.navigate(Navigation.History)
}