package watchusers.testtask.com.ui.contract

import watchusers.testtask.com.data.model.User

interface UsersListContract {

    interface View : ProgressBar {

        fun showUsers(users: List<User>)

        fun showError(error: String?)

        fun openUserInfoPage(userId: Int)

        fun scrollToPositionWithOffset(itemPosition: Int, itemOffset: Int)

        fun showMessage(message: Int)
    }

    interface Presenter : Lifecycle<View> {

        fun getUsers()

        fun onUserItemClicked(userId: Int)

        fun loadNextUserPage()

        fun setRecyclerViewItemsToInitial()

        fun saveRecyclerPosition(itemPosition: Int, itemOffset: Int)
    }
}