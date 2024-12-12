package com.orka.network

import com.orka.lib.models.Credential
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

internal interface LoginApiService {
    @POST("token/")
    @FormUrlEncoded
    suspend fun getCredential(
        @Field("username") username: String,
        @Field("password") password: String
    ): Credential?
}