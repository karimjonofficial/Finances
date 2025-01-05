package com.orka.history.models

import com.orka.receive.ReceiveItem

@ConsistentCopyVisibility
data class ReceiveItemModel private constructor(
    val name: String,
    val amount: Int
) {
    constructor(item: ReceiveItem) : this(
        name = item.product.name,
        amount = item.amount
    )
}
