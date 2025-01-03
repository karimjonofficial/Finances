package com.orka.core

data class UpdateProductModel(
    val id: Int,
    val name: String,
    val price: Double,
    val description: String,
    val categoryId: Int
)