package com.orka.stock.fixtures

import com.orka.core.ReceiveDataSource
import com.orka.receive.ReceiveItem
import com.orka.receive.Receive

class ReceiveDataSourceImpl : ReceiveDataSource {
    override suspend fun add(items: List<ReceiveItem>, price: Double, comment: String): Receive? {
        TODO("Not yet implemented")
    }
}