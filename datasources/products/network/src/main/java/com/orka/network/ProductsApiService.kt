package com.orka.network

import com.orka.products.Product
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

internal interface ProductsApiService {
    @Headers("accept: application/json")
    @GET("v1/products/")
    suspend fun get(
        @Header("Authorization") authorizationHeader: String,
        @Query("category") categoryId: Int
    ): List<Product>?

    @Headers("accept: application/json")
    @GET("v1/products/")
    suspend fun get(@Header("Authorization") authorizationHeader: String): List<Product>?

    @Headers("accept: application/json")
    @FormUrlEncoded
    @POST("v1/products/")
    suspend fun post(
        @Header("Authorization") authorizationHeader: String,
        @Field("name") name: String,
        @Field("price") price: Double,
        @Field("description") description: String,
        @Field("category") categoryId: Int
    ): Product?
}