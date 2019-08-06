package watchusers.testtask.com.ui.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.no_internet_tooltip.view.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import watchusers.testtask.com.R
import watchusers.testtask.com.data.network.Network
import watchusers.testtask.com.data.network.NetworkCheckerUtil

abstract class BaseFragment : Fragment(), KodeinAware {

    override val kodein: Kodein by closestKodein()

    private val networkChecker: NetworkCheckerUtil by instance()
    private var isNetworkReconnected = false

    private lateinit var disposable: Disposable

    protected abstract fun bindView()
    protected abstract fun unbindView()
    protected abstract fun reloadData()
    protected abstract fun getViewLayout(): Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(getViewLayout(), container, false)
        bindView()
        setNetworkChecker()
        return view
    }

    private fun setNetworkChecker() {
        disposable = networkChecker.checkNetwork()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    when (it) {
                        Network.AVALIABLE -> {
                            if (isNetworkReconnected) {
                                reloadData()
                                isNetworkReconnected = false
                                hideCustomToast()
                            }
                        }

                        Network.NONE -> {
                            isNetworkReconnected = true
                            showCustomToast(getString(R.string.fragment_base_no_internet_connection))
                        }

                        null -> {
                        }
                    }
                },
                {
                    showError(it.message)
                })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        unbindView()
        disposable.dispose()
    }

    fun showLoadingIndicator() {
        activity!!.progressBar?.visibility = View.VISIBLE
    }

    fun hideLoadingIndicator() {
        activity!!.progressBar?.visibility = View.GONE
    }

    fun showError(error: String?) {
        Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
    }

    fun showCustomToast(message: String) {
        activity!!.no_internet_tooltip_include!!.textView_no_internet_tooltip.text = message
        activity!!.no_internet_tooltip_include!!.visibility = View.VISIBLE
    }

    fun hideCustomToast() {
        activity!!.no_internet_tooltip_include!!.textView_no_internet_tooltip.text = ""
        activity!!.no_internet_tooltip_include!!.visibility = View.GONE
    }
}