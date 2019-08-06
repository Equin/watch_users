package watchusers.testtask.com

import org.kodein.di.KodeinAware
import org.kodein.di.conf.ConfigurableKodein
import android.app.Application
import android.content.Context
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton
import watchusers.testtask.com.di.InjectionModules

open class TestApplication : Application(), KodeinAware {

    override val kodein = ConfigurableKodein()

    override fun onCreate() {
        super.onCreate()
        setupKodein()
    }

    private fun setupKodein() {
        kodein.apply {
            mutable = true
            clear()
            addImport(InjectionModules.appDependencyInjectionModule)
            addImport(Kodein.Module("common modules") {
                bind<Context>() with singleton { this@TestApplication }
            })
        }
    }
}