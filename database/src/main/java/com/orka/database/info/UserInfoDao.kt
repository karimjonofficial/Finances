package com.orka.database.info

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserInfoDao {
    @Insert
    suspend fun insert(userInfo: UserInfoModel)

    @Update
    suspend fun update(userInfo: UserInfoModel)

    @Query("select * from info where id = 1")
    suspend fun select(): UserInfoModel?

    @Query("update info set token = null, refresh = null where id = 1")
    suspend fun deleteCredentials()
}