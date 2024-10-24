package com.orka.finances.features.login.data.source.network

import com.orka.finances.features.login.data.models.UserCredentials
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface LoginApiService {
    @POST("token/")
    @FormUrlEncoded
    suspend fun access(
        @Field("username") username: String,
        @Field("password") password: String
    ): UserCredentials?
}