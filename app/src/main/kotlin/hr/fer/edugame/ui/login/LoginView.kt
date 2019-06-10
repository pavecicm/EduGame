package hr.fer.edugame.ui.login

import hr.fer.edugame.ui.shared.base.BaseView

interface LoginView : BaseView {
    fun navigateToHome()
    fun navigateToHomeNoInternet()
    fun showSuccesfullLogin()
    fun showAuthFailed()
}