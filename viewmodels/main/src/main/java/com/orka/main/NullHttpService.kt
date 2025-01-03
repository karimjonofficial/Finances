package com.orka.main

import com.orka.core.HttpService

object NullHttpService : HttpService {

    override suspend fun <T> invoke(
        onException: ((Exception) -> T)?,
        request: suspend () -> T
    ): T? {
        TODO("Not yet implemented")
    }
}