package com.orka.singleton

class ScopedContainer internal constructor(
    private val retrofit: Retrofit,
    private val credential: Credential,
    private val navigateToWarehouse: (Int) -> Unit
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

    val homeScreenViewModel: HomeScreenViewModel by lazy {
        HomeScreenViewModel(
            dataSource = categoriesDataSource,
            httpService = httpService,
            navigate = navigateToWarehouse
        )
    }

    val basketViewModel: BasketScreenViewModel by lazy {
        BasketScreenViewModel(httpService, basketDataSource, saleDataSource)
    }

    val historyViewModel: HistoryScreenViewModel by lazy {
        HistoryScreenViewModel(receiveDataSource, saleDataSource, httpService)
    }



    fun transientContainer(navigateToStockItem: (Int) -> Unit): TransientContainer {
        return TransientContainer(navigateToStockItem)
    }
}