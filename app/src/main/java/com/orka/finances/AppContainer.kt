package com.orka.finances

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.orka.finances.features.login.data.source.LoginDataSource
import com.orka.finances.features.login.data.source.network.LoginApiService
import com.orka.finances.features.login.data.source.network.RemoteLoginDataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET

private const val BASE_URL = "http://185.204.2.105/api/"
private const val AMPHIBIANS_BASE_URL = "https://android-kotlin-fun-mars-server.appspot.com"

@Serializable
data class Amphibian(
    @SerialName("name") val name: String,
    @SerialName("type") val type: String,
    @SerialName("description") val description: String,
    @SerialName("img_src") val imageSrc: String,
)

class AppContainer {
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(BASE_URL)
        .build()

    private val amphibiansRetrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(AMPHIBIANS_BASE_URL)
        .build()

    private val loginApiService: LoginApiService by lazy {
        retrofit.create(LoginApiService::class.java)
    }

    private val amphibiansApiService: AmphibiansApiService by lazy {
        amphibiansRetrofit.create(amphibiansApiService::class.java)
    }

    val loginDataSource: LoginDataSource = RemoteLoginDataSource(loginApiService)
    val amphibiansDataSource = NetworkAmphibiansDataSource(amphibiansApiService)
}

interface AmphibiansApiService {
    @GET("amphibians")
    suspend fun get(): List<Amphibian>
}


class NetworkAmphibiansDataSource(private val apiService: AmphibiansApiService) {
    suspend fun getAmphibians(): List<Amphibian> {
        return apiService.get()
    }
}

class AmphibiansViewModel(private val dataSource: NetworkAmphibiansDataSource) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeScreenUiState>(HomeScreenUiState.Initial)
    val uiState: StateFlow<HomeScreenUiState> = _uiState

    init {
        viewModelScope.launch {
            try {
                _uiState.value = HomeScreenUiState.Loading
                val result = dataSource.getAmphibians()
                if(result.isEmpty()) {
                    _uiState.value = HomeScreenUiState.Fail("The list is empty")
                } else {
                    _uiState.value = HomeScreenUiState.Success(result)
                }
            } catch(e: Exception) {
                _uiState.value = HomeScreenUiState.Fail(e.message ?: "Unknown error")
            }
        }
    }
}

sealed interface HomeScreenUiState {
    data object Initial : HomeScreenUiState
    data object Loading : HomeScreenUiState
    data class Fail(val error: String) : HomeScreenUiState
    data class Success(val amphibians: List<Amphibian>) : HomeScreenUiState
}