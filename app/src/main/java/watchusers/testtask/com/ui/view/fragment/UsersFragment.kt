package watchusers.testtask.com.ui.view.fragment

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_users_list.*
import kotlinx.android.synthetic.main.item_user.*
import org.kodein.di.generic.instance
import watchusers.testtask.com.R
import watchusers.testtask.com.data.model.User
import watchusers.testtask.com.ui.adapter.UsersRecyclerViewAdapter
import watchusers.testtask.com.ui.contract.UsersListContract
import watchusers.testtask.com.ui.listener.LastElementScrollListener

class UsersFragment : BaseFragment(), UsersListContract.View,
    LastElementScrollListener.OnLastElementReachedListener {

    interface OnUsersFragmentInteractionListener {
        fun onUserItemClicked(userId: Int)
    }

    private val presenter: UsersListContract.Presenter by instance()

    private lateinit var userAdapter: UsersRecyclerViewAdapter

    override fun getViewLayout(): Int {
        return R.layout.fragment_users_list
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(context)
        recyclerView_users_list.layoutManager = layoutManager

        userAdapter = UsersRecyclerViewAdapter(OnUsersItemClickListener())
        recyclerView_users_list.adapter = userAdapter

        recyclerView_users_list.addOnScrollListener(LastElementScrollListener(this))

        presenter.getUsers()
    }

    override fun showUsers(users: List<User>) {
        userAdapter.addNewUsers(users)
    }

    override fun reloadData() {
        userAdapter.clear()
        presenter.getUsers()
    }

    override fun openUserInfoPage(userId: Int) {
        val extras = FragmentNavigatorExtras(
            imageView_user_item_avatar to "imageView_user_info_avatar",
            textView_user_full_name to "textView_user_info_full_name"
        )

        val bundle = bundleOf(ARG_USER_ID to userId)
        findNavController().navigate(R.id.action_usersFragment_to_userInfoFragment, bundle, null, extras)
    }

    override fun lastElementReached() {
        presenter.loadNextUserPage()
    }

    override fun scrollToPositionWithOffset(itemPosition: Int, itemOffset: Int) {
        this.recyclerView_users_list.postOnAnimation {
            val manager = recyclerView_users_list.layoutManager as LinearLayoutManager
            manager.scrollToPositionWithOffset(itemPosition, itemOffset)
            this.presenter.setRecyclerViewItemsToInitial()
        }
    }

    override fun showMessage(message: Int) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun bindView() {
        presenter.bind(this)
    }

    override fun unbindView() {
        presenter.unbind()
    }

    override fun onStop() {
        super.onStop()
        saveRecyclerViewPositionAndOffset()
    }

    private fun saveRecyclerViewPositionAndOffset() {
        val manager = recyclerView_users_list.layoutManager as LinearLayoutManager

        val itemPosition = manager.findFirstCompletelyVisibleItemPosition()
        val view = manager.findViewByPosition(itemPosition)
        val itemOffset = view?.top ?: 0

        this.presenter.saveRecyclerPosition(itemPosition, itemOffset)
    }

    private inner class OnUsersItemClickListener : OnUsersFragmentInteractionListener {
        override fun onUserItemClicked(userId: Int) {
            presenter.onUserItemClicked(userId)
        }
    }

    companion object {
        const val ARG_USER_ID = "arg_user_id"
    }
}

