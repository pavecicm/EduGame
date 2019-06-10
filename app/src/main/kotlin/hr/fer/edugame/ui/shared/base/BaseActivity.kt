package hr.fer.edugame.ui.shared.base

import android.app.AlertDialog
import android.os.Bundle
import android.support.annotation.DrawableRes
import android.support.annotation.LayoutRes
import android.support.annotation.StringRes
import android.widget.Toast
import dagger.android.support.DaggerAppCompatActivity
import hr.fer.edugame.R
import hr.fer.edugame.extensions.hideKeyboard
import hr.fer.edugame.extensions.showKeyboard
import hr.fer.edugame.ui.login.LoginActivity
import hr.fer.edugame.ui.shared.dialogs.LoadingDialog
import kotlinx.android.synthetic.main.toolbar.toolbar

abstract class BaseActivity : DaggerAppCompatActivity(), BaseView {

    @get:LayoutRes
    abstract val layoutRes: Int

    protected abstract fun providePresenter(): BasePresenter?

    private var dialog: AlertDialog? = null

    private var loadingDialog: LoadingDialog? = null

    open var goBack = true

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
            onBackPressed()
        }
    }

    protected open fun onNavigationIconClick() {
        onBackPressed()
    }

    override fun showLevelCompleted() {
        Toast.makeText(this, String.format(getString(R.string.congrats), 45), Toast.LENGTH_SHORT).show()
    }

    override fun showProgress() {
        if (loadingDialog?.isVisible != true) {
            loadingDialog = LoadingDialog()
            loadingDialog?.show(supportFragmentManager, "Loading dialog")
        }
    }

    override fun hideProgress() {
        if (loadingDialog != null) {
            loadingDialog?.dismiss()
            loadingDialog = null
        }
    }


    override fun showGameLost() {
        dialog = AlertDialog.Builder(this)
            .setMessage(String.format(getString(R.string.game_lost)))
            .setPositiveButton(getString(R.string.ok))
            { _, _ ->
                navigateToHome()
            }
            .create()
        dialog?.let {
            it.setCancelable(false)
            it.show()
        }
    }

    override fun showGameWon() {
        dialog = AlertDialog.Builder(this)
            .setMessage(String.format(getString(R.string.game_won)))
            .setPositiveButton(getString(R.string.ok))
            { _, _ ->
                navigateToHome()
            }
            .create()
        dialog?.let {
            it.setCancelable(false)
            it.show()
        }
    }

    private fun navigateToHome() {
        startActivity(LoginActivity.newInstance(this))
    }

    override fun finish() {
        dialog?.let {
            if (it.isShowing) {
                it.cancel()
            }
        }
        super.finish()
    }

    override fun onStop() {
        super.onStop()
        providePresenter()?.cancel()
    }

    override fun onBackPressed() {
        if (goBack) {
            super.onBackPressed()
        }
    }
}