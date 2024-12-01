package com.orka.finances.features.login.data.sources.network

import com.orka.finances.lib.data.credentials.Credentials
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface LoginApiService {
    @POST("token/")
    @FormUrlEncoded
    suspend fun getCredentials(
        @Field("username") username: String,
        @Field("password") password: String
    ): Credentials?
}