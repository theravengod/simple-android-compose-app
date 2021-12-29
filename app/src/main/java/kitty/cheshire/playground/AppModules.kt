package kitty.cheshire.playground

import android.annotation.SuppressLint
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.features.observer.*
import io.ktor.client.request.*
import io.ktor.http.*
import kitty.cheshire.playground.ui.screens.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import timber.log.Timber
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager


val appModules = module {
    viewModel { parameters ->
        MainViewModel()
    }
    single { // Net client
        HttpClient(OkHttp) {
            engine {
                config {
                    followRedirects(true)
                    connectTimeout(50, TimeUnit.SECONDS)
                    callTimeout(50, TimeUnit.SECONDS)
                    readTimeout(50, TimeUnit.SECONDS)

                    if (BuildConfig.DEBUG) {
                        val naiveTrustManager = @SuppressLint("CustomX509TrustManager")
                        object : X509TrustManager {
                            override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
                            override fun checkClientTrusted(certs: Array<X509Certificate>, authType: String) = Unit
                            override fun checkServerTrusted(certs: Array<X509Certificate>, authType: String) = Unit
                        }
                        val insecureSocketFactory = SSLContext.getInstance("TLSv1.2").apply {
                            val trustAllCerts = arrayOf<TrustManager>(naiveTrustManager)
                            init(null, trustAllCerts, SecureRandom())
                        }.socketFactory
                        sslSocketFactory(insecureSocketFactory, naiveTrustManager)
                        hostnameVerifier { _, _ -> true }
                    }
                }

            }

            install(JsonFeature) {
                serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                })
            }

            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        Timber.d("KTor: %s", message)
                    }
                }
                level = LogLevel.ALL
            }

            install(ResponseObserver) {
                onResponse { response ->
                    Timber.d("HTTP status:", "${response.status.value}")
                }
            }

            install(DefaultRequest) {
                header(HttpHeaders.ContentType, ContentType.Application.Json)
            }
        }
    }
}
