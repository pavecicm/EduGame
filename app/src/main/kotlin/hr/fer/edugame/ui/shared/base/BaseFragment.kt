package hr.fer.edugame.ui.shared.base

import android.content.Context
import android.os.Bundle
import android.support.annotation.DrawableRes
import android.support.annotation.LayoutRes
import android.support.annotation.StringRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.support.DaggerFragment
import hr.fer.edugame.R
import kotlinx.android.synthetic.main.toolbar.toolbar

abstract class BaseFragment : DaggerFragment(), BaseView {

    @get:LayoutRes
    abstract val layoutRes: Int

    protected abstract fun providePresenter(): BasePresenter?

    private lateinit var baseActivity: BaseActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutRes, container, false)
    }

    override fun onAttach(context: Context?) {
        if (context is BaseActivity) {
            baseActivity = context
        } else {
            throw RuntimeException("Every activity MUST extend BaseActivity!")
        }
        super.onAttach(requireContext())
    }

    override fun showKeyboard() = baseActivity.showKeyboard()

    override fun hideKeyboard() = baseActivity.hideKeyboard()

    override fun showLevelCompleted() = baseActivity.showLevelCompleted()

    override fun showProgress() = baseActivity.showProgress()

    override fun hideProgress() = baseActivity.hideProgress()

    override fun showGameLost() = baseActivity.showGameLost()

    override fun showGameWon() = baseActivity.showGameWon()

    override fun onDestroyView() {
        super.onDestroyView()
        providePresenter()?.let {
            it.cancel()
        }
    }

    protected fun setupToolbar(@StringRes titleRes: Int, @DrawableRes iconRes: Int = R.drawable.ic_arrow_left_navigation) {
        setToolbarTitle(titleRes)
        setToolbarIcon(iconRes)
    }

    protected fun setToolbarTitle(@StringRes titleRes: Int) {
        toolbar?.setTitle(titleRes)
    }

    protected fun setToolbarTitle(title: String) {
        toolbar?.title = title
    }

    protected fun setToolbarIcon(@DrawableRes iconRes: Int) {
        toolbar?.setNavigationIcon(iconRes)
        toolbar?.setNavigationOnClickListener {
            baseActivity.onBackPressed()
        }
    }
}