package com.orka.network

import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

internal interface ReceiveApiService {

    @Headers("accept: application/json", "Content-Type: application/json")
    @POST("v1/receive/")
    suspend fun post(
        @Header("Authorization") authorizationHeader: String,
        @Body body: RequestModel
    ): ResponseModel?
}