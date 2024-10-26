package com.orka.finances

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.Update
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.orka.finances.features.home.data.sources.network.CategoriesApiService
import com.orka.finances.features.home.data.sources.network.RemoteCategoriesDataSource
import com.orka.finances.features.login.data.sources.LoginDataSource
import com.orka.finances.features.login.data.sources.network.LoginApiService
import com.orka.finances.features.login.data.sources.network.RemoteLoginDataSource
import com.orka.finances.lib.data.UserCredentials
import com.orka.finances.lib.data.UserCredentialsDataSource
import com.orka.finances.lib.data.UserData
import com.orka.finances.lib.data.UserDataSource
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.create

private const val BASE_URL = "http://185.204.2.105/api/"

class AppContainer {
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(BASE_URL).build()

    private inline fun <reified T> createByRetrofit(): Lazy<T> {
        return lazy { retrofit.create<T>() }
    }

    private val loginApiService: LoginApiService by createByRetrofit<LoginApiService>()
    private val categoriesApiService: CategoriesApiService by createByRetrofit<CategoriesApiService>()

    val credentialsSource: UserCredentialsDataSource by lazy { LocalUserCredentialsDataSource() }

    val loginDataSource: LoginDataSource by lazy {
        RemoteLoginDataSource(loginApiService) {
            credentialsSource.setCredentials(UserCredentials(it.token, it.refresh))
        }
    }

    val categoriesDataSource = RemoteCategoriesDataSource(categoriesApiService)
}

private class LocalUserCredentialsDataSource : UserCredentialsDataSource {
    private var credentials: UserCredentials? = null

    override suspend fun getCredentials(): UserCredentials {
        return credentials ?: UserCredentials("token", "refresh")
    }

    override fun setCredentials(credentials: UserCredentials) {
        this.credentials = credentials
    }
}

@Dao
private interface UserDataDao : UserDataSource {
    @Insert
    override suspend fun insert(userData: UserData)

    @Update
    override suspend fun update(userData: UserData)

    @Query("select * from info where id = 1")
    override suspend fun getUserData(): UserData?
}

@Database(entities = [UserData::class], version = 1, exportSchema = false)
private abstract class FinancesDb : RoomDatabase() {
    abstract fun userDataDao(): UserDataDao

    companion object {
        @Volatile
        private var Instance: FinancesDb? = null

        fun getDatabase(context: Context): FinancesDb {
            return Instance ?: synchronized(this) {
                Room
                    .databaseBuilder(context, FinancesDb::class.java, "finances_db")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}

//TODO Store credentials in db