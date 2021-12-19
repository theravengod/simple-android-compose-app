package kitty.cheshire.playground.net

import android.util.Log
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.coroutines.awaitObjectResult
import com.github.kittinunf.fuel.serialization.kotlinxDeserializerOf
import kitty.cheshire.playground.db.daos.CoffeeDAO
import kitty.cheshire.playground.db.model.Coffee
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.serialization.builtins.ListSerializer

// Using random data from https://random-data-api.com/documentation
class Net(private val coffeeDAO: CoffeeDAO) {

    private val insertCoroutineScope = CoroutineScope(Dispatchers.IO + Job())

    suspend fun getSomeRandomCoffee(howMany: Int = 10) {
        Fuel.get("https://random-data-api.com/api/coffee/random_coffee?size=$howMany")
            .awaitObjectResult(kotlinxDeserializerOf(loader = ListSerializer(Coffee.serializer())))
            .fold(
                success = { coffeeList ->
                    insertCoroutineScope.launch {
                        coffeeList.forEach { coffeeDAO.insertCoffee(it) }
                    }
                },
                failure = {
                    Log.e("NET", "ERROR : ${it.exception.localizedMessage}")
                    Log.e("NET", "ERROR : ", it.cause)
                }
            )
    }

}