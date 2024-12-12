package com.orka.data

interface UserInfoDataSource {
    suspend fun add(userData: UserInfo)
    suspend fun update(userData: UserInfo)
    suspend fun get(): UserInfo?
    suspend fun unauthorize()
}