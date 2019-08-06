package watchusers.testtask.com.di.app

import androidx.room.Room
import watchusers.testtask.com.data.db.AppDatabase
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import watchusers.testtask.com.di.InjectionModule
import java.util.concurrent.Executors

object CommonInjectionModule : InjectionModule {

    private const val DATABASE_NAME = "watchUser"

    override val module = Kodein.Module(this.javaClass.name) {

        bind<AppDatabase>() with singleton {
            Room
                .databaseBuilder(
                    instance(),
                    AppDatabase::class.java,
                    DATABASE_NAME
                )
                .fallbackToDestructiveMigration()
                .setQueryExecutor(Executors.newCachedThreadPool())
                .build()
        }
    }
}