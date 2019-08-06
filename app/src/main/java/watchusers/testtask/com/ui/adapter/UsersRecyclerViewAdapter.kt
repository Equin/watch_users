package watchusers.testtask.com.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import watchusers.testtask.com.R
import watchusers.testtask.com.ui.view.fragment.UsersFragment.OnUsersFragmentInteractionListener
import kotlinx.android.synthetic.main.item_user.view.*
import watchusers.testtask.com.data.model.User
import watchusers.testtask.com.ui.UsersDiffCallback

class UsersRecyclerViewAdapter(
    private val itemClickListener: OnUsersFragmentInteractionListener?
) : RecyclerView.Adapter<UsersRecyclerViewAdapter.ViewHolder>() {

    private val onClickListener: View.OnClickListener
    private val usersList: ArrayList<User> = ArrayList()

    init {
        onClickListener = View.OnClickListener { v ->
            val userId = v.getTag(R.id.tag_int_user_id) as Int
            itemClickListener?.onUserItemClicked(userId)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_user, parent, false)
        view.setOnClickListener(onClickListener)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(usersList[position])
    }

    override fun getItemCount(): Int = usersList.size

    fun addNewUsers(data: List<User>) {
        if (data != usersList) {
            val newUserList = ArrayList<User>()
            newUserList.addAll(usersList)
            newUserList.addAll(data)
            notifyDiffUtilAboutChanges(newUserList)
        }
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        private val userFullName: TextView = view.textView_user_full_name
        private val userAvatar: ImageView = view.imageView_user_item_avatar

        fun bindView(item: User) {

            val fullName = "${item.firstName} ${item.lastName}"
            this.userFullName.text = fullName

            Glide
                .with(this.itemView)
                .load(item.avatar)
                .centerCrop()
                .placeholder(R.drawable.ic_avatar)
                .into(this.userAvatar)

            with(this.view) {
                setTag(R.id.tag_int_user_id, item.id)
            }
        }
    }

    private fun notifyDiffUtilAboutChanges(newUserList: List<User>) {
        val diffResult = DiffUtil.calculateDiff(UsersDiffCallback(usersList, newUserList))
        this.usersList.clear()
        this.usersList.addAll(newUserList)
        diffResult.dispatchUpdatesTo(this)
    }

    fun clear() {
        usersList.clear()
    }
}
