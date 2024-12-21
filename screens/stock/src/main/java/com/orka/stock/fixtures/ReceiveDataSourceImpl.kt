package com.orka.stock.fixtures

import com.orka.core.ReceiveDataSource
import com.orka.core.models.PostRequestModel
import com.orka.receive.Receive

class ReceiveDataSourceImpl : ReceiveDataSource {

    override suspend fun get(): List<Receive>? {
        return null
    }

    override suspend fun add(body: PostRequestModel): Receive? {
        return null
    }
}