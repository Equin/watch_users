package watchusers.testtask.com.ui.presenter

import androidx.annotation.UiThread
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import watchusers.testtask.com.ui.contract.Lifecycle

abstract class BasePresenter<V> : Lifecycle<V> {

    protected var view: V? = null

    private val compositeDisposable = CompositeDisposable()

    protected fun Disposable.disposeOnUnbind() {
        compositeDisposable.add(this)
    }

    @UiThread
    override fun bind(view: V) {
        this.view = view
    }

    @UiThread
    override fun unbind() {
        compositeDisposable.clear()
        view = null
    }
}