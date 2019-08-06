package watchusers.testtask.com.di

import watchusers.testtask.com.di.main.MainInjectionModule
import watchusers.testtask.com.di.network.NetworkInjectionModule
import org.kodein.di.Kodein
import watchusers.testtask.com.di.app.CommonInjectionModule

object InjectionModules {

    val appDependencyInjectionModule = Kodein.Module("Main injection module") {
        import(NetworkInjectionModule.module)
        import(CommonInjectionModule.module)
        import(MainInjectionModule.module)
    }
}