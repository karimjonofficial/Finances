package com.orka.history.models

import com.orka.sale.Sale
import kotlinx.datetime.LocalTime
import com.orka.datetime.toLocalTime

@ConsistentCopyVisibility
data class SaleModel private constructor(
    val price: Double,
    val time: LocalTime,
    val comment: String,
    val items: List<SaleItemModel>
) {
    constructor(sale: Sale) : this(
        price = sale.price,
        time = sale.datetime.toLocalTime(),
        comment = sale.comment,
        items = sale.items.map { SaleItemModel(it) }
    )
}
