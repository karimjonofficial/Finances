package com.orka.network

import com.orka.base.Product
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

internal interface ProductsApiService {
    @Headers("accept: application/json")
    @GET("v1/products/")
    suspend fun get(
        @Header("Authorization") authorizationHeader: String,
        categoryId: Int
    ): List<Product>?

    @Headers("accept: application/json")
    @FormUrlEncoded
    @POST("v1/categories/")
    suspend fun post(
        @Header("Authorization") authorizationHeader: String,
        @Field("name") name: String,
        @Field("price") price: Double,
        @Field("description") description: String,
        @Field("img_src") imgSrc: String
    ): Product?
}

internal interface FakeProductsApiService {
    @Headers("accept: application/json")
    @GET("v1/products/")
    suspend fun get(
        @Header("Authorization") authorizationHeader: String
    ): List<Product>?

    @Headers("accept: application/json")
    @FormUrlEncoded
    @POST("v1/categories/")
    suspend fun post(
        @Header("Authorization") authorizationHeader: String,
        @Field("name") name: String,
        @Field("price") price: Double,
        @Field("description") description: String,
        @Field("img_src") imgSrc: String
    ): Product?
}