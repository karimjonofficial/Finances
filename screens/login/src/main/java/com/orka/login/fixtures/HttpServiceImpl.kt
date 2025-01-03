package com.orka.login.fixtures

import com.orka.core.HttpService

class HttpServiceImpl : HttpService {
    override suspend fun <T> invoke(
        onException: ((Exception) -> T)?,
        request: suspend () -> T
    ): T? {
        TODO("Not yet implemented")
    }
}
