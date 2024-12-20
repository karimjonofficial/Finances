package com.orka.network

import com.orka.credentials.Credential
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

internal interface CredentialsApiService {
    @POST("token/")
    @FormUrlEncoded
    suspend fun get(
        @Field("username") username: String,
        @Field("password") password: String
    ): Credential?
}