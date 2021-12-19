package kitty.cheshire.playground.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kitty.cheshire.playground.db.daos.CoffeeDAO
import kitty.cheshire.playground.db.model.Coffee

@Database(entities = [Coffee::class], version = 1, exportSchema = false)
abstract class AppDB : RoomDatabase() {

    abstract fun coffeeDAO(): CoffeeDAO

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: AppDB? = null

        fun getDatabase(context: Context): AppDB {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDB::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }

}