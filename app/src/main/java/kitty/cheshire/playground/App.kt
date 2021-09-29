package kitty.cheshire.playground

import android.app.Application
import kitty.cheshire.playground.db.AppDB
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext

class App: Application() {

    private val appDB by lazy { AppDB.getDatabase(this) }

    val elementsDAO by lazy { appDB.elementDAO() }

    override fun onCreate() {
        super.onCreate()

        instance = this

        initStuff()
    }

    private fun initStuff() {
        GlobalContext.startKoin {
            // Koin Android logger
            androidLogger()
            //inject Android context
            androidContext(this@App)
            // use modules
            modules(appModules)
        }
    }


    companion object {
        lateinit var instance: App
    }
}