package watchusers.testtask.com.ui.listener

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import watchusers.testtask.com.ui.presenter.UsersListPresenter.Companion.usersPerPage
import watchusers.testtask.com.ui.adapter.UsersRecyclerViewAdapter

class LastElementScrollListener(private val fragment: Fragment) : RecyclerView.OnScrollListener() {

    interface OnLastElementReachedListener {

        fun lastElementReached()
    }

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)

        if (RecyclerView.SCROLL_STATE_IDLE == newState) {

            if (isLastElement(recyclerView)
                && recyclerView.adapter is UsersRecyclerViewAdapter
                && fragment is OnLastElementReachedListener && isActionAllowed(recyclerView)
            ) {

                (fragment as OnLastElementReachedListener).lastElementReached()
            }
        }
    }

    private fun isLastElement(recyclerView: RecyclerView): Boolean {
        val manager = recyclerView.layoutManager as LinearLayoutManager?
        return if (manager != null) {
            recyclerView.adapter!!.itemCount - 1 == manager.findLastVisibleItemPosition()
        } else false
    }

    private fun isActionAllowed(recyclerView: RecyclerView): Boolean {
        return recyclerView.adapter!!.itemCount >= usersPerPage
    }
}