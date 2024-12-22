package com.orka.sale

import kotlinx.datetime.Instant

data class Sale(
    val id: Int,
    val items: List<SaleItem>,
    val price: Double,
    val datetime: Instant,
    val comment: String,
    val userId: Int
)
