package hr.fer.edugame.ui.login

import com.google.firebase.auth.FirebaseUser
import hr.fer.edugame.ui.shared.base.BaseView

interface LoginView: BaseView {
    fun navigateToHome(user: FirebaseUser)
    fun navigateToHomeNoInternet()
    fun showSuccesfullLogin()
    fun showAuthFailed()
}