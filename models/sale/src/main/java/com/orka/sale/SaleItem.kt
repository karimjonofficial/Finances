package com.orka.sale

import com.orka.products.Product

data class SaleItem(
    val id: Int,
    val product: Product,
    val amount: Int
)
