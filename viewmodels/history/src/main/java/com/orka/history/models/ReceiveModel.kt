package com.orka.history.models

import com.orka.datetime.toLocalTime
import com.orka.receive.Receive
import kotlinx.datetime.LocalTime

@ConsistentCopyVisibility
data class ReceiveModel private  constructor(
    val price: Double,
    val time: LocalTime,
    val comment: String,
    val items: List<ReceiveItemModel>
) {
    constructor(receive: Receive) : this(
        price = receive.price,
        time = receive.datetime.toLocalTime(),
        comment = receive.comment,
        items = receive.items.map { ReceiveItemModel(it) }
    )
}
