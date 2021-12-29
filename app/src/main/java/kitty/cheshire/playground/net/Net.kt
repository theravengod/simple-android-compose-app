package kitty.cheshire.playground.net

import io.ktor.client.*
import io.ktor.client.request.*
import kitty.cheshire.playground.db.daos.CoffeeDAO
import kitty.cheshire.playground.db.model.Coffee
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject
import timber.log.Timber
import kotlin.Result.Companion.failure
import kotlin.Result.Companion.success

// Using random data from https://random-data-api.com/documentation
class Net(private val coffeeDAO: CoffeeDAO) {

    private val insertCoroutineScope = CoroutineScope(Dispatchers.IO + Job())

    private val ktorHttpClient: HttpClient by inject(HttpClient::class.java)

    suspend fun getSomeRandomCoffee(howMany: Int = 10): Result<Int> {
        return try {
            val coffeeList = ktorHttpClient.get<List<Coffee>>("https://random-data-api.com/api/coffee/random_coffee") {
                parameter("size", "$howMany")
            }
            if (coffeeList.isNotEmpty()) {
                insertCoroutineScope.launch {
                    coffeeList.forEach { coffeeDAO.insertCoffee(it) }
                }
            }
            success(coffeeList.size)
        } catch (e: Exception) {
            Timber.e(e, "NET ERR")
            failure(e)
        }
    }
}

