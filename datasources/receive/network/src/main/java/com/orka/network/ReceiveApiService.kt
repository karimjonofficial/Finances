package com.orka.network

import com.orka.core.models.PostResponseModel
import com.orka.core.models.PostRequestModel
import com.orka.receive.Receive
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

internal interface ReceiveApiService {

    @Headers("accept: application/json", "Content-Type: application/json")
    @POST("v1/receive/")
    suspend fun post(
        @Header("Authorization") authorizationHeader: String,
        @Body body: PostRequestModel
    ): PostResponseModel?

    @Headers("accept: application/json", "Content-Type: application/json")
    @GET("v1/receive/")
    suspend fun get(@Header("Authorization") authorizationHeader: String): List<Receive>?
}