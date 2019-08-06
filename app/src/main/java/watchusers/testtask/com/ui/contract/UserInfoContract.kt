package watchusers.testtask.com.ui.contract

import watchusers.testtask.com.data.model.User

interface UserInfoContract {

    interface View : ProgressBar {

        fun showUser(user: User)

        fun showError(error: String?)
    }

    interface Presenter : Lifecycle<View> {

        fun getUser(userId: Int)
    }
}