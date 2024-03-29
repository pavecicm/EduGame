package hr.fer.edugame.ui.login

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import hr.fer.edugame.R
import hr.fer.edugame.extensions.intentFor
import hr.fer.edugame.extensions.setThrottlingClickListener
import hr.fer.edugame.ui.main.MainActivity
import hr.fer.edugame.ui.shared.base.BaseActivity
import kotlinx.android.synthetic.main.activity_login.loginBtn
import kotlinx.android.synthetic.main.activity_login.username
import javax.inject.Inject

class LoginActivity : BaseActivity(), LoginView {

    companion object {
        fun newInstance(context: Context) = context.intentFor<LoginActivity>()
    }

    override val layoutRes: Int = R.layout.activity_login
    override fun providePresenter(): LoginPresenter = presenter

    @Inject
    lateinit var presenter: LoginPresenter

    override fun onStart() {
        super.onStart()
        presenter.onStart()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initUI()
    }

    fun initUI() {
        loginBtn.setThrottlingClickListener {
            if (username.text.isNullOrEmpty()) {
                username.error = getString(R.string.username_error)
            } else {
                presenter.signIn(username.text.toString())
            }
        }
    }

    override fun navigateToHome() {
        startActivity(MainActivity.newInstance(this))
    }

    override fun navigateToHomeNoInternet() {
        Toast.makeText(applicationContext, getString(R.string.no_internet), Toast.LENGTH_LONG).show()
        startActivity(MainActivity.newInstance(this))
    }

    override fun showSuccesfullLogin() {
        Toast.makeText(applicationContext, "Successful Login", Toast.LENGTH_SHORT).show()
    }

    override fun showAuthFailed() {
        Toast.makeText(applicationContext, getString(R.string.login_failed), Toast.LENGTH_LONG).show()
    }

//    override fun showProgress() {
//        loginBtn.isEnabled = false
//        anonymous.isEnabled = false
//        progressLayout.show()
//    }

//    override fun hideProgress() {
//        loginBtn.isEnabled = true
//        anonymous.isEnabled = true
//        progressLayout.hide()
//    }
}