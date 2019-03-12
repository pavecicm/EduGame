package hr.fer.edugame.ui.login

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseUser
import hr.fer.edugame.R
import hr.fer.edugame.extensions.intentFor
import hr.fer.edugame.extensions.setThrottlingClickListener
import hr.fer.edugame.ui.main.MainActivity
import hr.fer.edugame.ui.shared.base.BaseActivity
import kotlinx.android.synthetic.main.activity_login.anonymous
import kotlinx.android.synthetic.main.activity_login.email
import kotlinx.android.synthetic.main.activity_login.loginBtn
import kotlinx.android.synthetic.main.activity_login.password
import javax.inject.Inject

class LoginActivity : BaseActivity(), LoginView {

    companion object {
        const val EXTRA_USER = "EXTRA_USER"

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
            presenter.loginToFirebase(this, email.text.toString(), password.text.toString())
        }
        anonymous.setThrottlingClickListener {
            presenter.continueAsAnonymous()
        }
    }

    override fun navigateToHome(user: FirebaseUser) {
        startActivity(MainActivity.newInstance(this, user))
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
}