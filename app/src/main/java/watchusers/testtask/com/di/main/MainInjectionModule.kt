package watchusers.testtask.com.di.main

import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton
import watchusers.testtask.com.data.db.AppDatabase
import watchusers.testtask.com.data.db.dao.UserDao
import watchusers.testtask.com.di.InjectionModule
import watchusers.testtask.com.domain.interactors.MainInteractor
import watchusers.testtask.com.domain.repository.UserRepository
import watchusers.testtask.com.domain.repository.UserRepositoryContract
import watchusers.testtask.com.ui.contract.UserInfoContract
import watchusers.testtask.com.ui.contract.UsersListContract
import watchusers.testtask.com.ui.presenter.UserInfoPresenter
import watchusers.testtask.com.ui.presenter.UsersListPresenter

object MainInjectionModule : InjectionModule {

    override val module = Kodein.Module(this.javaClass.name) {

        bind<UsersListContract.Presenter>() with provider {
            UsersListPresenter(instance())
        }

        bind<UserRepositoryContract>() with singleton {
            UserRepository(instance(), instance())
        }

        bind<UserDao>() with singleton {
            instance<AppDatabase>().userDao()
        }

        bind<UserInfoContract.Presenter>() with provider {
            UserInfoPresenter(instance())
        }

        bind<MainInteractor>() with provider {
            MainInteractor(instance())
        }
    }
}