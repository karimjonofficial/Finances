package com.orka.core

data class AddProductModel(
    val name: String,
    val price: Double,
    val description: String,
    val categoryId: Int
)
