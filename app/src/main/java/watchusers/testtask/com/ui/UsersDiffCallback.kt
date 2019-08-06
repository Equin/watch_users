package watchusers.testtask.com.ui

import androidx.recyclerview.widget.DiffUtil
import watchusers.testtask.com.data.model.User

class UsersDiffCallback(
    private val oldUsers: List<User>,
    private val newUsers: List<User>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return this.oldUsers.size
    }

    override fun getNewListSize(): Int {
        return this.newUsers.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return this.oldUsers[oldItemPosition].id == this.newUsers[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return this.oldUsers[oldItemPosition].firstName == this.newUsers[newItemPosition].firstName
    }
}