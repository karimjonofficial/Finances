object Modules {
    private val containers = ":containers"
    val database = ":database"
    private val datasources = ":datasources"
    val di = ":di"
    private val formatter = ":formatter"
    private val fsm = ":fsm"
    private val http = ":http"
    private val lib = ":lib"
    private val models = ":models"
    val res = ":res"
    private val screens = ":screens"
    val unauthorizer = ":unauthorizer"
    private val viewModels = ":viewmodels"

    object Containers {
        val main = "$containers:main"
        val core = "$containers${AbstractionType.core}"
    }
    object DataSources {

        object Basket {
            val core = "$datasources${ModelFamilies.basket}${AbstractionType.core}"
            val local = "$datasources${ModelFamilies.basket}${DataSourceTypes.local}"
        }

        object Category {
            val core = "$datasources${ModelFamilies.category}${AbstractionType.core}"
            val network = "$datasources${ModelFamilies.category}${DataSourceTypes.network}"
        }

        object Credential {
            val core = "$datasources${ModelFamilies.credential}${AbstractionType.core}"
            val network = "$datasources${ModelFamilies.credential}${DataSourceTypes.network}"
        }

        object Info {
            val core = "$datasources${ModelFamilies.info}${AbstractionType.core}"
        }

        object Product {
            val core = "$datasources${ModelFamilies.product}${AbstractionType.core}"
            val network = "$datasources${ModelFamilies.product}${DataSourceTypes.network}"
        }

        object Receive {
            val core = "$datasources${ModelFamilies.receive}${AbstractionType.core}"
            val network = "$datasources${ModelFamilies.receive}${DataSourceTypes.network}"
        }

        object Sale {
            val core = "$datasources${ModelFamilies.sale}${AbstractionType.core}"
            val network = "$datasources${ModelFamilies.sale}${DataSourceTypes.network}"
        }

        object Stock {
            val core = "$datasources${ModelFamilies.stock}${AbstractionType.core}"
            val network = "$datasources${ModelFamilies.stock}${DataSourceTypes.network}"
        }
    }
    object Formatters {
        val core = "$formatter${AbstractionType.core}"
        val uz = "$formatter${CountryCodes.uz}"
    }
    object Fsm {
        val main = "$fsm:main"
    }
    object Http {
        private val service = "$http:service"
        val status = "$http:status"

        object Service {
            val core = "$service:core"
        }
    }
    object Lib {

        private val extensions = "$lib:extensions"
        val log = "$lib:log"
        val tests = "$lib:tests"
        private val ui = "$lib:ui"

        object Extensions {
            val string = "$extensions:string"
        }

        object Ui {
            val components = "$ui:components"
            val input = "$ui:input"
            private val navigation = "$ui:navigation"

            object Navigation {
                val items = "$navigation:items"
            }
        }
    }
    object Models {

        val basket = "$models${ModelFamilies.basket}"
        val category = "$models${ModelFamilies.category}"
        val credential = "$models${ModelFamilies.credential}"
        val info = "$models${ModelFamilies.info}"
        val product = "$models${ModelFamilies.product}"
        val receive = "$models${ModelFamilies.receive}"
        val sale = "$models${ModelFamilies.sale}"
        val stock = "$models${ModelFamilies.stock}"
    }
    object Screens {

        val basket = "$screens${ScreenFamilies.basket}"
        val history = "$screens${ScreenFamilies.history}"
        val home = "$screens${ScreenFamilies.home}"
        val login = "$screens${ScreenFamilies.login}"
        val products = "$screens${ScreenFamilies.products}"
        val stock = "$screens${ScreenFamilies.stock}"
        val warehouse = "$screens${ScreenFamilies.warehouse}"
    }
    object ViewModels {

        val basket = "$viewModels${ScreenFamilies.basket}"
        val core = "$viewModels${AbstractionType.core}"
        val history = "$viewModels${ScreenFamilies.history}"
        val home = "$viewModels${ScreenFamilies.home}"
        val login = "$viewModels${ScreenFamilies.login}"
        val main = "$viewModels:main"
        val products = "$viewModels${ScreenFamilies.products}"
        val stock = "$viewModels${ScreenFamilies.stock}"
    }
}

object AbstractionType {
    val core = ":core"
}
object DataSourceTypes {

    val local = ":local"
    val network = ":network"
}
data object ModelFamilies {

    val basket = ":basket"
    val category = ":categories"
    val credential = ":credentials"
    val info = ":info"
    val product = ":products"
    val receive = ":receive"
    val sale = ":sale"
    val stock = ":stock"
}
data object ScreenFamilies {

    val basket = ":basket"
    val history = ":history"
    val home = ":home"
    val login = ":login"
    val products = ":products"
    val stock = ":stock"
    val warehouse = ":warehouse"
}
object CountryCodes {
    val uz = ":uz"
}