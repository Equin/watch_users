package watchusers.testtask.com.ui.presenter

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import watchusers.testtask.com.R
import watchusers.testtask.com.data.model.User
import watchusers.testtask.com.domain.interactors.MainInteractor
import watchusers.testtask.com.ui.contract.UsersListContract

class UsersListPresenter(
    private val mainInteractor: MainInteractor
) : BasePresenter<UsersListContract.View>(), UsersListContract.Presenter {

    private var currentPage: Int = 1
    private var totalPages: Int = 0
    private var isActionAllowed: Boolean = true
    private var recyclerItemPosition = invalidPosition
    private var recyclerItemOffset = 0

    override fun getUsers() {
        view?.showLoadingIndicator()

        if (recyclerItemPosition == invalidPosition) {
            currentPage = 1
            getUsers(currentPage, false)
        } else {
            getUsers(currentPage, true)
        }
    }

    override fun onUserItemClicked(userId: Int) {
        view?.openUserInfoPage(userId)
    }

    override fun loadNextUserPage() {
        if (isActionAllowed) {
            isActionAllowed = false
            if (totalPages > currentPage) {
                currentPage++
                getUsers(currentPage, false)
            } else {
                isActionAllowed = true
                view?.showMessage(R.string.fragment_users_list_no_more_pages)
            }
        }
    }

    override fun setRecyclerViewItemsToInitial() {
        recyclerItemOffset = 0
        recyclerItemPosition = invalidPosition
    }

    override fun saveRecyclerPosition(itemPosition: Int, itemOffset: Int) {
        recyclerItemOffset = itemOffset
        recyclerItemPosition = itemPosition
    }

    private fun getUsers(page: Int, isPositionRestoreLoad: Boolean) {
        mainInteractor.getUsers(page, usersPerPage, isPositionRestoreLoad)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    totalPages = it.totalPages
                    view?.showUsers(it.data)
                    isActionAllowed = true

                    scrollToUserIfPossible(it.data)
                    view?.hideLoadingIndicator()
                },
                {
                    view?.showError(it.message)
                    view?.hideLoadingIndicator()
                    isActionAllowed = true
                }
            )
            .disposeOnUnbind()
    }

    private fun scrollToPosition(itemPosition: Int, itemOffset: Int) {
        if (this.view != null) {
            view?.scrollToPositionWithOffset(itemPosition, itemOffset)
        }
    }

    private fun scrollToUserIfPossible(users: List<User>) {
        if (users.isNotEmpty() && recyclerItemPosition != invalidPosition
            && users[recyclerItemPosition].id != 0
        ) {
            this.scrollToPosition(recyclerItemPosition, recyclerItemOffset)
        }
    }

    companion object {
        const val invalidPosition: Int = -1
        const val usersPerPage: Int = 10
    }
}