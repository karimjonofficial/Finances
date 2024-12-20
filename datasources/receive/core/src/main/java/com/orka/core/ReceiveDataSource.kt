package com.orka.core

import com.orka.receive.Receive
import com.orka.receive.ReceiveItem

interface ReceiveDataSource {
    suspend fun add(
        items: List<ReceiveItem>,
        price: Double,
        comment: String
    ): Receive?
}