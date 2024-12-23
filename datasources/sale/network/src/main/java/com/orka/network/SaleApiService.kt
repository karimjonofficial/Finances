package com.orka.network

import com.orka.core.models.PostSaleRequestModel
import com.orka.core.models.PostSaleResponseModel
import com.orka.network.models.GetSaleResponseModel
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface SaleApiService {

    @Headers("accept: application/json", "Content-Type: application/json")
    @GET("v1/sales/")
    suspend fun get(@Header("Authorization") authorizationHeader: String): List<GetSaleResponseModel>?

    @Headers("accept: application/json", "Content-Type: application/json")
    @POST("v1/sales/")
    suspend fun post(
        @Header("Authorization") authorizationHeader: String,
        @Body body: PostSaleRequestModel
    ): PostSaleResponseModel?
}
