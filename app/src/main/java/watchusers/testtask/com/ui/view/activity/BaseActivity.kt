package watchusers.testtask.com.ui.view.activity

import androidx.appcompat.app.AppCompatActivity
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein

abstract class BaseActivity : AppCompatActivity(), KodeinAware {

    override val kodein: Kodein by closestKodein()

    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
        bindView()
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindView()
    }

    protected abstract fun bindView()

    protected abstract fun unbindView()
}