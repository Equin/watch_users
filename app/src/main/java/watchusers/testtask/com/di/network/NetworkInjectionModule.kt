package watchusers.testtask.com.di.network

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import watchusers.testtask.com.data.network.*
import watchusers.testtask.com.di.InjectionModule

object NetworkInjectionModule : InjectionModule {

    override val module = Kodein.Module(this.javaClass.name) {

        bind<OkHttpClient>() with singleton {
            HttpClientBuilder().init()
        }

        bind<RxJava2CallAdapterFactory>() with singleton {
            RxJava2CallAdapterFactory.create()
        }

        bind<GsonConverterFactory>() with singleton {
            GsonConverterFactory.create(GsonBuilder().setLenient().serializeNulls().create())
        }

        bind<RetrofitManager>() with singleton {
            NetworkBuilder.create(instance(), instance(), instance())
        }

        bind<NetworkCheckerUtil>() with singleton {
            NetworkCheckerUtilImpl(instance())
        }
    }
}