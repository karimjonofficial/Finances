package com.orka.finances.features.home.data.sources.network

import com.orka.finances.features.home.models.Category
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

interface CategoriesApiService {
    @Headers("accept: application/json")
    @GET("v1/categories/")
    suspend fun get(@Header("Authorization") token: String): List<Category>?
}