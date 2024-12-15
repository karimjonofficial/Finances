package com.orka.network

import com.orka.base.Category
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

internal interface CategoriesApiService {
    @Headers("accept: application/json")
    @GET("v1/categories/")
    suspend fun get(@Header("Authorization") authorizationHeader: String): List<Category>?

    @Headers("accept: application/json")
    @FormUrlEncoded
    @POST("v1/categories/")
    suspend fun post(
        @Header("Authorization") authorizationHeader: String,
        @Field("name") name: String,
        @Field("description") description: String
    ): Category?
}