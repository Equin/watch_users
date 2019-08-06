package watchusers.testtask.com.ui.view.fragment


import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import kotlinx.android.synthetic.main.fragment_user_info.*
import org.kodein.di.generic.instance
import watchusers.testtask.com.R
import watchusers.testtask.com.data.model.User
import watchusers.testtask.com.ui.contract.UserInfoContract
import watchusers.testtask.com.ui.BlurTransformation
import watchusers.testtask.com.ui.view.fragment.UsersFragment.Companion.ARG_USER_ID

class UserInfoFragment : BaseFragment(), UserInfoContract.View {

    private val presenter: UserInfoContract.Presenter by instance()

    private var userId: Int? = null

    override fun getViewLayout(): Int {
        return R.layout.fragment_user_info
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.getUser(userId!!)
    }

    override fun showUser(user: User) {
        val fullName = "${user.firstName} ${user.lastName}"
        textView_user_info_full_name.text = fullName
        textView_user_info_email.text = user.email

        Glide
            .with(this)
            .load(user.avatar)
            .circleCrop()
            .placeholder(R.drawable.ic_avatar)
            .into(imageView_user_info_avatar)

        Glide
            .with(this)
            .load(user.avatar)
            .transform(CenterCrop(), BlurTransformation(requireContext()))
            .into(imageView_avatar_background)
    }

    override fun reloadData() {
        presenter.getUser(userId!!)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            userId = it.getInt(ARG_USER_ID)
        }
    }

    override fun bindView() {
        presenter.bind(this)
    }

    override fun unbindView() {
        presenter.unbind()
    }
}
