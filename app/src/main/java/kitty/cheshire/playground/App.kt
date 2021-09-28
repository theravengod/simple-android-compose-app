package kitty.cheshire.playground

import android.app.Application
import kitty.cheshire.playground.db.AppDB

class App: Application() {

    val appDB by lazy { AppDB.getDatabase(this) }
    val elementsDAO by lazy { appDB.elementDAO() }

    override fun onCreate() {
        super.onCreate()

        instance = this
    }

    companion object {
        lateinit var instance: App
    }
}