package com.orka.finances.application.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.orka.finances.lib.data.info.UserInfo

@Database(entities = [UserInfo::class], version = 1, exportSchema = false)
abstract class FinancesDb : RoomDatabase() {
    abstract fun userDataDao(): UserInfoDao

    companion object {
        @Volatile
        private var Instance: FinancesDb? = null

        fun getDatabase(context: Context): FinancesDb {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, FinancesDb::class.java, "finances_db")
                    .fallbackToDestructiveMigration()
                    .build().also { Instance = it }
            }
        }
    }
}