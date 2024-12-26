package com.orka.di

import com.orka.core.HttpService
import com.orka.status.HttpStatus
import com.orka.unauthorizer.Unauthorizer
import retrofit2.HttpException

class HttpServiceImpl(
    private val unauthorizer: Unauthorizer
) : HttpService {
    override suspend fun invoke(
        request: suspend () -> Unit,
        onException: ((Exception) -> Unit)?
    ) {
        try { request() } catch(e: Exception) {
            if(isUnauthorizedException(e))
                unauthorizer.unauthorize()
            else onException?.invoke(e)
        }
    }

    override suspend fun invoke(
        request: suspend () -> Unit,
        onException: ((Exception) -> Unit)?,
        onUnauthorize: (() -> Unit)?
    ) {
        try { request() } catch(e: Exception) {
            if(isUnauthorizedException(e))
                onUnauthorize?.invoke()
            else onException?.invoke(e)
        }
    }

    override suspend fun invoke(request: suspend () -> Unit) {
        try { request() } catch(e: Exception) {
            if(isUnauthorizedException(e))
                unauthorizer.unauthorize()
        }
    }

    override suspend fun invoke(request: suspend () -> Unit, onUnauthorize: (() -> Unit)?) {
        try { request() } catch(e: Exception) {
            if(isUnauthorizedException(e))
                unauthorizer.unauthorize()
        }
    }

    override suspend fun invokeOnly(request: suspend () -> Unit) {
        try { request() } catch(_: Exception) {}
    }

    private fun isUnauthorizedException(e: Exception): Boolean {
        return e is HttpException && e.code() == HttpStatus.Unauthorized.code
    }
}