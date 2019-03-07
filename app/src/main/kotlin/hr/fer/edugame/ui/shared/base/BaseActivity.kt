package hr.fer.edugame.ui.shared.base

import android.os.Bundle
import android.support.annotation.DrawableRes
import android.support.annotation.LayoutRes
import android.support.annotation.StringRes
import android.widget.Toast
import dagger.android.support.DaggerAppCompatActivity
import hr.fer.edugame.R
import hr.fer.edugame.extensions.hideKeyboard
import hr.fer.edugame.extensions.showKeyboard
import kotlinx.android.synthetic.main.toolbar.toolbar

abstract class BaseActivity : DaggerAppCompatActivity(), BaseView {

    @get:LayoutRes
    abstract val layoutRes: Int

    protected abstract fun providePresenter(): BasePresenter?

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutRes)
        setupToolbar()
    }

    override fun showKeyboard() = currentFocus?.showKeyboard() ?: Unit

    override fun hideKeyboard() = currentFocus?.hideKeyboard() ?: Unit

    private fun setupToolbar() {
        toolbar?.setNavigationOnClickListener { onNavigationIconClick() }
    }

    protected fun setToolbarTitle(@StringRes titleRes: Int) {
        toolbar?.setTitle(titleRes)
    }

    protected fun setToolbarTitle(title: String) {
        toolbar?.title = title
    }

    protected fun setToolbarIcon(@DrawableRes iconRes: Int) {
        toolbar?.setNavigationIcon(iconRes)
    }

    protected open fun onNavigationIconClick() {
        onBackPressed()
    }

    override fun showLevelCompleted() {
        Toast.makeText(this, String.format(getString(R.string.congrats), 45), Toast.LENGTH_SHORT).show()
    }

    override fun showProgress() {

    }

    override fun hideProgress() {

    }
}