package com.orka.finances.features.stock.data.sources.network

import com.orka.finances.features.stock.models.StockItem
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface StockApiService {
    @Headers("accept: application/json")
    @GET("v1/stocks/")
    suspend fun get(
        @Header("Authorization") authHeader: String,
        @Query("category") categoryId: Int
    ): List<StockItem>?
}