package com.orka.login.fixtures

import com.orka.core.HttpService

class HttpServiceImpl : HttpService {

    override suspend fun invoke(request: suspend () -> Unit, onException: ((Exception) -> Unit)?) {
        throw Exception()
    }

    override suspend fun invoke(
        request: suspend () -> Unit,
        onException: ((Exception) -> Unit)?,
        onUnauthorize: (() -> Unit)?
    ) {
        throw Exception()
    }

    override suspend fun invoke(request: suspend () -> Unit) {
        throw Exception()
    }

    override suspend fun invoke(request: suspend () -> Unit, onUnauthorize: (() -> Unit)?) {
        throw Exception()
    }

    override suspend fun invokeOnly(request: suspend () -> Unit) {
        throw Exception()
    }

}
