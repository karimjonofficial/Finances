package com.orka.finances.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class Navigation {
    @Serializable
    data object Home : Navigation()

    @Serializable
    data class Product(@SerialName("item_id") val productId: Int) : Navigation()

    @Serializable
    data class Warehouse(@SerialName("category_id") val categoryId: Int) : Navigation()

    @Serializable
    data object History : Navigation()

    @Serializable
    data object Basket : Navigation()
}

fun NavController.navigateToHome() {
    this.navigate(Navigation.Home) {
        popUpTo(graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}

fun NavController.navigateToProduct(itemId: Int) {
    this.navigate(Navigation.Product(itemId)) {
        popUpTo(graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}

fun NavController.navigateToWarehouse(categoryId: Int) {
    this.navigate(Navigation.Warehouse(categoryId)) {
        popUpTo(graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}

fun NavController.navigateToBasket() {
    this.navigate(Navigation.Basket) {
        popUpTo(graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}

fun NavController.navigateToHistory() {
    this.navigate(Navigation.History) {
        popUpTo(graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}