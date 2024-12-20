package com.orka.login.fixtures

import com.orka.core.HttpService

class HttpServiceImpl : HttpService {

    override suspend fun invoke(request: suspend () -> Unit, onException: ((Exception) -> Unit)?) {
        TODO("Not yet implemented")
    }

    override suspend fun invoke(
        request: suspend () -> Unit,
        onException: ((Exception) -> Unit)?,
        onUnauthorize: (() -> Unit)?
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun invoke(request: suspend () -> Unit) {
        TODO("Not yet implemented")
    }

    override suspend fun invoke(request: suspend () -> Unit, onUnauthorize: (() -> Unit)?) {
        TODO("Not yet implemented")
    }

    override suspend fun invokeOnly(request: suspend () -> Unit) {
        TODO("Not yet implemented")
    }

}
