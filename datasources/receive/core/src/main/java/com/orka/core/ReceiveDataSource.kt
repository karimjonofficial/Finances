package com.orka.core

import com.orka.core.models.PostRequestModel
import com.orka.receive.Receive

interface ReceiveDataSource {

    suspend fun get(): List<Receive>?
    suspend fun add(body: PostRequestModel): Receive?
}