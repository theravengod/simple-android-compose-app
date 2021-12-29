package kitty.cheshire.playground

import android.app.Application
import kitty.cheshire.playground.db.AppDB
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext
import org.koin.core.logger.Level
import timber.log.Timber

class App: Application() {

    private val appDB by lazy { AppDB.getDatabase(this) }

    val coffeeDAO by lazy { appDB.coffeeDAO() }

    override fun onCreate() {
        super.onCreate()

        instance = this

        initStuff()
    }

    private fun initStuff() {
        Timber.plant(Timber.DebugTree())
        GlobalContext.startKoin {
            // Koin Android logger
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE) // For issue https://github.com/InsertKoinIO/koin/issues/1188
            // Inject Android context
            androidContext(this@App)
            // Use modules
            modules(appModules)
        }
    }


    companion object {
        lateinit var instance: App
    }
}