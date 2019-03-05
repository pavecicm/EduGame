package hr.fer.edugame.ui.login

import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseUser
import hr.fer.edugame.R
import hr.fer.edugame.extensions.setThrottlingClickListener
import hr.fer.edugame.ui.MainActivity
import hr.fer.edugame.ui.shared.base.BaseActivity
import hr.fer.edugame.ui.shared.base.BasePresenter
import kotlinx.android.synthetic.main.activity_login.email
import kotlinx.android.synthetic.main.activity_login.loginBtn
import kotlinx.android.synthetic.main.activity_login.password
import javax.inject.Inject

class LoginActivity : BaseActivity(), LoginView {

    companion object {
        const val EXTRA_USER = "EXTRA_USER"
    }

    override val layoutRes: Int = R.layout.activity_login
    override fun providePresenter(): BasePresenter? = null

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
    }

    override fun navigateToHome(user: FirebaseUser) {
        startActivity(MainActivity.newInstance(this, user))
    }

    override fun showSuccesfullLogin() {
        Toast.makeText(applicationContext, "Successful Login", Toast.LENGTH_SHORT).show()
    }

    override fun showAuthFailed() {
        Toast.makeText(applicationContext, "Login Failed. Check Email and Password Again!", Toast.LENGTH_SHORT).show()
    }
}