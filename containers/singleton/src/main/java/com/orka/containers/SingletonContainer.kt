package com.orka.containers

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.orka.basket.BasketScreenViewModel
import com.orka.containers.res.baseUrl
import com.orka.core.CategoriesDataSource
import com.orka.core.CredentialManager
import com.orka.core.Formatter
import com.orka.core.HttpService
import com.orka.core.ProductsDataSource
import com.orka.core.ReceiveDataSource
import com.orka.core.SaleDataSource
import com.orka.core.StockDataSource
import com.orka.credentials.Credential
import com.orka.history.HistoryScreenViewModel
import com.orka.home.HomeScreenViewModel
import com.orka.local.InMemoryBasketDataSource
import com.orka.local.LocalFormatter
import com.orka.login.LoginScreenViewModel
import com.orka.network.RemoteCategoriesDataSource
import com.orka.network.RemoteCredentialDataSource
import com.orka.network.RemoteProductsDataSource
import com.orka.network.RemoteReceiveDataSource
import com.orka.network.RemoteSaleDataSource
import com.orka.network.RemoteStockDataSource
import com.orka.product.ProductScreenViewModel
import com.orka.warehouse.WarehouseScreenViewModel
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

class SingletonContainer(
    private val credentialManager: CredentialManager,
    private val unauthorize: () -> Unit
) {

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl).build()

    private val httpService: HttpService by lazy { HttpServiceImpl(unauthorize) }
    private val basketDataSource by lazy { InMemoryBasketDataSource.create() }
    private val credentialsDataSource by lazy { RemoteCredentialDataSource.create(retrofit) }

    val loginScreenViewModel by lazy {
        LoginScreenViewModel(credentialsDataSource, credentialManager, httpService)
    }
    val formatter: Formatter by lazy { LocalFormatter() }

    inner class ScopedContainer internal constructor(
        private val credential: Credential,
        private val navigateToWarehouse: (Int) -> Unit,
        private val navigateToProduct: (Int) -> Unit
    ) {

        private val categoriesDataSource: CategoriesDataSource by lazy {
            RemoteCategoriesDataSource.create(retrofit, credential)
        }
        private val receiveDataSource: ReceiveDataSource by lazy {
            RemoteReceiveDataSource.create(retrofit, credential)
        }
        private val stockDataSource: StockDataSource by lazy {
            RemoteStockDataSource.create(retrofit, credential)
        }
        private val productsDataSource: ProductsDataSource by lazy {
            RemoteProductsDataSource.create(retrofit, credential)
        }
        private val saleDataSource: SaleDataSource by lazy {
            RemoteSaleDataSource.create(retrofit, credential)
        }
        private val warehouseViewModels = emptyMap<Int, WarehouseScreenViewModel>().toMutableMap()
        private val productViewModels = emptyMap<Int, ProductScreenViewModel>().toMutableMap()

        val homeScreenViewModel: HomeScreenViewModel by lazy {
            HomeScreenViewModel(
                dataSource = categoriesDataSource,
                httpService = httpService,
                navigate = navigateToWarehouse
            )
        }
        val basketViewModel: BasketScreenViewModel by lazy {
            BasketScreenViewModel(httpService, basketDataSource, saleDataSource) {
                warehouseViewModels.forEach { map -> map.value.refresh() }
            }
        }
        val historyViewModel: HistoryScreenViewModel by lazy {
            HistoryScreenViewModel(receiveDataSource, saleDataSource, httpService)
        }

        fun productViewModel(id: Int): ProductScreenViewModel {
            return productViewModels[id] ?: ProductScreenViewModel(
                dataSource = productsDataSource,
                productId = id,
                httpService = httpService
            ).apply { productViewModels[id] = this }
        }

        fun warehouseViewModel(categoryId: Int): WarehouseScreenViewModel {
            return warehouseViewModels[categoryId] ?: WarehouseScreenViewModel(
                categoryId = categoryId,
                httpService = httpService,
                productsDataSource = productsDataSource,
                stockDataSource = stockDataSource,
                receiveDataSource = receiveDataSource,
                basketDataSource = basketDataSource,
                navigateToProductScreen = navigateToProduct
            ).apply { warehouseViewModels[categoryId] = this }
        }
    }

    fun scopedContainer(
        credential: Credential,
        navigateToWarehouse: (Int) -> Unit,
        navigateToProduct: (Int) -> Unit
    ): ScopedContainer {
        return ScopedContainer(credential, navigateToWarehouse, navigateToProduct)
    }
}