package com.orka.finances.lib.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "info")
data class UserInfo(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo("token") val token: String?,
    @ColumnInfo("refresh") val refresh: String?
)
