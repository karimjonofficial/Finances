package com.orka.history.models

import com.orka.sale.SaleItem

@ConsistentCopyVisibility
data class SaleItemModel private constructor (
    val name: String,
    val amount: Int
) {
    constructor(item: SaleItem) : this(
        name = item.product.name,
        amount = item.amount
    )
}
