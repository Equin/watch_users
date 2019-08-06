package watchusers.testtask.com.data.network

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import watchusers.testtask.com.BuildConfig
import java.util.concurrent.TimeUnit

class HttpClientBuilder {

    fun init(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()

        if (BuildConfig.DEBUG) {
            interceptor.level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
            .addInterceptor(Interceptor { chain ->
                val origin = chain.request()

                return@Interceptor chain.proceed(origin)
            })
            .addInterceptor(interceptor)
            .connectTimeout(TIMEOUT_VAL, TimeUnit.SECONDS)
            .build()
    }

    companion object {
        private const val TIMEOUT_VAL: Long = 20
    }
}