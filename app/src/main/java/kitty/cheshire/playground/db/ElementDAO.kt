package kitty.cheshire.playground.db

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ElementDAO {

    @Query("SELECT * FROM Element")
    suspend fun getAllElements(): List<Element>

    @Query("SELECT * FROM Element")
    fun getAllElementsAsFlow(): Flow<List<Element>>

    @Query("SELECT * FROM Element WHERE id = :id LIMIT 1")
    suspend fun getElement(id: Int): Element
}