package watchusers.testtask.com.ui.contract

import androidx.annotation.UiThread

interface Lifecycle<V> {

    @UiThread
    fun bind(view: V)

    @UiThread
    fun unbind()
}