package com.orka.history.fixtures

import com.orka.core.ReceiveDataSource
import com.orka.receive.Receive
import com.orka.receive.ReceiveItem

class InMemoryReceiveDataSource : ReceiveDataSource {

    override suspend fun get(): List<Receive>? {
        return null
    }

    override suspend fun add(items: List<ReceiveItem>, price: Double, comment: String): Receive? {
        return null
    }
}
