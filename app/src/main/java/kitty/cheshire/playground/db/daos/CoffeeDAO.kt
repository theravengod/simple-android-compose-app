package kitty.cheshire.playground.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kitty.cheshire.playground.db.model.Coffee
import kotlinx.coroutines.flow.Flow

@Dao
interface CoffeeDAO {

    @Query("SELECT * FROM Coffee")
    fun getAllCoffeeEntries(): Flow<List<Coffee>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCoffee(item: Coffee)
}