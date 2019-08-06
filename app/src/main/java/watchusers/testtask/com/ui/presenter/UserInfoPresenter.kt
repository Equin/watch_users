package watchusers.testtask.com.ui.presenter

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import watchusers.testtask.com.domain.interactors.MainInteractor
import watchusers.testtask.com.ui.contract.UserInfoContract

class UserInfoPresenter(
    private val mainInteractor: MainInteractor
) : BasePresenter<UserInfoContract.View>(), UserInfoContract.Presenter {

    override fun getUser(userId: Int) {

        view?.showLoadingIndicator()

        mainInteractor.getUser(userId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    view?.showUser(it)
                    view?.hideLoadingIndicator()
                },
                {
                    view?.showError(it.message)
                    view?.hideLoadingIndicator()
                }
            )
            .disposeOnUnbind()
    }
}